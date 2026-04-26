package kr.higu.peelie.user.domain;

public interface UserReader {
    User getUser(Long userId);
    User getUser(String userPublicId);
    User findUser(User.Provider provider, String oid);
}
