package kr.higu.peelie.avatar.application;

import kr.higu.peelie.avatar.domain.AvatarService;
import kr.higu.peelie.avatar.domain.ChatMessage;
import kr.higu.peelie.avatar.domain.ChatRoom;
import kr.higu.peelie.avatar.domain.SendMessageCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvatarFacade {

    private final AvatarService avatarService;

    public SseEmitter sendMessage(String userPublicId, SendMessageCommand command) {
        return avatarService.sendMessage(userPublicId, command);
    }

    public List<ChatRoom> getChatRooms(String userPublicId) {
        return avatarService.getChatRooms(userPublicId);
    }

    public List<ChatMessage> getMessages(String userPublicId, String chatRoomPublicId) {
        return avatarService.getMessages(userPublicId, chatRoomPublicId);
    }
}
