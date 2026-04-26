package kr.higu.peelie.friendship.domain;

import java.util.List;
import java.util.Optional;

public interface FriendshipReader {
    List<Friendship> getFriendShips(Long userId);
    Optional<Friendship> findFriendship(Long userId, Long friendUserId);
}
