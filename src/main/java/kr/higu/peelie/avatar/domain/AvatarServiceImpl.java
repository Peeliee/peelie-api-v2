package kr.higu.peelie.avatar.domain;

import kr.higu.peelie.common.exception.EntityNotFoundException;
import kr.higu.peelie.common.exception.UnauthorizedException;
import kr.higu.peelie.user.domain.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {
    private static final int RECENT_HISTORY_LIMIT = 10;

    private final UserReader userReader;

    private final AsyncTaskExecutor virtualThreadTaskExecutor;
    private final AvatarChatClient avatarChatClient;

    private final ChatRoomReader chatRoomReader;
    private final ChatRoomStore chatRoomStore;
    private final ChatMessageReader chatMessageReader;
    private final ChatMessageStore chatMessageStore;

    @Override
    public SseEmitter sendMessage(String userPublicId, SendMessageCommand command) {
        SseEmitter emitter = new SseEmitter(0L);

        virtualThreadTaskExecutor.execute(() -> {
            try {
                Long userId = userReader.getUser(userPublicId).getId();
                Long friendId = userReader.getUser(command.getFriendPublicId()).getId();
                ChatRoom chatRoom = chatRoomReader.findByUserIdAndFriendId(userId, friendId)
                        .orElseGet(() -> chatRoomStore.store(ChatRoom.init(userId, friendId)));

                List<ChatMessage> history = chatMessageReader.getRecentMessages(chatRoom.getId(), RECENT_HISTORY_LIMIT);

                // meta 정보 처리 - SSE 시작
                emitter.send(SseEmitter.event()
                        .name("meta")
                        .data(new ChatStreamMeta(chatRoom.getChatRoomPublicId(), userPublicId, command.getFriendPublicId())));

                // 본문 chunk 처리
                String answer = avatarChatClient.streamChat(
                        chatRoom.getFriendId(),
                        command.getMessage(),
                        history,
                        content -> {
                            try {
                                emitter.send(SseEmitter.event()
                                        .name("delta")
                                        .data(Map.of("content", content)));
                            } catch (Exception e) {
                                throw new IllegalStateException("채팅 delta 전송에 실패했습니다.", e);
                            }
                        }
                );

                chatRoom.touch();
                chatRoomStore.store(chatRoom);
                chatMessageStore.storeAll(List.of(
                        ChatMessage.of(chatRoom, ChatMessage.ChatRole.USER, command.getMessage()),
                        ChatMessage.of(chatRoom, ChatMessage.ChatRole.AVATAR, answer)
                ));

                emitter.send(SseEmitter.event()
                        .name("done")
                        .data(Map.of(
                                "chatRoomPublicId", chatRoom.getChatRoomPublicId(),
                                "answer", answer
                        )));
                emitter.complete();
            } catch (Exception e) {
                try {
                    emitter.send(SseEmitter.event()
                            .name("error")
                            .data(Map.of("message", e.getMessage() == null ? "메시지 전송 실패" : e.getMessage())));
                    emitter.complete();
                } catch (Exception sendException) {
                    emitter.completeWithError(sendException);
                }
            }
        });

        return emitter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatRoom> getChatRooms(String userPublicId) {
        Long userId = userReader.getUser(userPublicId).getId();
        return chatRoomReader.getChatRooms(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessage> getMessages(String userPublicId, String chatRoomPublicId) {
        Long userId = userReader.getUser(userPublicId).getId();
        ChatRoom chatRoom = chatRoomReader.findByChatRoomPublicId(chatRoomPublicId)
                .orElseThrow(() -> new EntityNotFoundException("해당 채팅방이 존재하지 않습니다. chatRoomPublicId=" + chatRoomPublicId));

        validateChatRoomOwner(userId, chatRoom);
        return chatMessageReader.getMessages(chatRoom.getId());
    }

    private void validateChatRoomOwner(Long userId, ChatRoom chatRoom) {
        if (!chatRoom.getUserId().equals(userId)) {
            throw new UnauthorizedException("해당 채팅방에 접근할 수 없습니다. chatRoomPublicId=" + chatRoom.getChatRoomPublicId());
        }
    }
}
