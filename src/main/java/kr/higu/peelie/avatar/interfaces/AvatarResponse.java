package kr.higu.peelie.avatar.interfaces;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class AvatarResponse {

    private AvatarResponse() {
    }

    @Getter
    @AllArgsConstructor
    public static class ChatRoomItem {
        private final String chatRoomPublicId;
        private final Long friendId;
        private final LocalDateTime lastMessageAt;
    }

    @Getter
    @AllArgsConstructor
    public static class ChatRoomList {
        private final List<ChatRoomItem> chatRooms;
    }

    @Getter
    @AllArgsConstructor
    public static class ChatMessageItem {
        private final Long id;
        private final String role;
        private final String content;
        private final LocalDateTime createdAt;
    }

    @Getter
    @AllArgsConstructor
    public static class ChatMessageList {
        private final List<ChatMessageItem> messages;
    }
}
