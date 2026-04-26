package kr.higu.peelie.avatar.domain;

import java.util.List;
import java.util.function.Consumer;

public interface AvatarChatClient {

    String streamChat(
            Long friendId,
            String message,
            List<ChatMessage> history,
            Consumer<String> deltaListener
    );
}
