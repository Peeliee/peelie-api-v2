package kr.higu.peelie.avatar.domain;

import java.util.List;
import java.util.Optional;

public interface ChatRoomReader {

    Optional<ChatRoom> findByChatRoomPublicId(String chatRoomPublicId);

    Optional<ChatRoom> findByUserIdAndFriendId(Long userId, Long friendId);

    List<ChatRoom> getChatRooms(Long userId);
}
