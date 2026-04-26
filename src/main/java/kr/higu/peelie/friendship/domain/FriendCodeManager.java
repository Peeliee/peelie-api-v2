package kr.higu.peelie.friendship.domain;

import java.util.Optional;

public interface FriendCodeManager {
    FriendCode getOrCreate(Long userId);
    Optional<Long> findUserIdByCode(String code);
}
