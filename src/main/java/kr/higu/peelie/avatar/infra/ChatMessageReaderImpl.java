package kr.higu.peelie.avatar.infra;

import kr.higu.peelie.avatar.domain.ChatMessage;
import kr.higu.peelie.avatar.domain.ChatMessageReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatMessageReaderImpl implements ChatMessageReader {

    private final ChatMessageRepository chatMessageRepository;

    @Override
    public List<ChatMessage> getRecentMessages(Long chatRoomId, int limit) {
        return chatMessageRepository.findByChatRoomIdOrderByIdDesc(chatRoomId, PageRequest.of(0, limit));
    }

    @Override
    public List<ChatMessage> getMessages(Long chatRoomId) {
        return chatMessageRepository.findByChatRoomIdOrderByCreatedAtAscIdAsc(chatRoomId);
    }

    @Override
    public List<ChatMessage> getLatestMessages(List<Long> chatRoomIds) {
        if (chatRoomIds.isEmpty()) {
            return List.of();
        }
        return chatMessageRepository.findLatestMessagesByChatRoomIds(chatRoomIds);
    }
}
