package kr.higu.peelie.user.domain;

import kr.higu.peelie.user.domain.oauth.Provider;

public interface UserReader {
    User getUser(Long userId);
    User getUser(String userPublicId);
    User getUserByFriendCode(String friendCode);
    User findUser(Provider provider, String oid);
}
