package kr.higu.peelie.avatar.domain;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface AvatarService {

    SseEmitter sendMessage(String userPublicId, SendMessageCommand command);

    List<ChatRoom> getChatRooms(String userPublicId);

    List<ChatMessage> getMessages(String userPublicId, String chatRoomPublicId);
}
