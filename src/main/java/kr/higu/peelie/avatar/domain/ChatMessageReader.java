package kr.higu.peelie.avatar.domain;

import java.util.List;

public interface ChatMessageReader {

    List<ChatMessage> getRecentMessages(Long chatRoomId, int limit);

    List<ChatMessage> getMessages(Long chatRoomId);

    List<ChatMessage> getLatestMessages(List<Long> chatRoomIds);
}
