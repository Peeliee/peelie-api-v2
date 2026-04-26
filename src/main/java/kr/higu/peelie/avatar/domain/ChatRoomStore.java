package kr.higu.peelie.avatar.domain;

public interface ChatRoomStore {

    ChatRoom store(ChatRoom chatRoom);

    void delete(ChatRoom chatRoom);
}
