package kr.higu.peelie.avatar.domain;

import java.util.List;

public interface ChatMessageStore {

    void storeAll(List<ChatMessage> chatMessages);

    void deleteByChatRoomId(Long chatRoomId);
}
