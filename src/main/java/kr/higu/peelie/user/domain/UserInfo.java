package kr.higu.peelie.user.domain;

import lombok.Getter;

@Getter
public class UserInfo {
    private final Long id;
    private final String userPublicId;
    private final User.Provider provider;
    private final String oid;
    private final String email;
    private final String nickname;
    private final boolean isOnboarded;

    public UserInfo(User user) {
        this.id = user.getId();
        this.userPublicId = user.getUserPublicId();
        this.provider = user.getProvider();
        this.oid = user.getOid();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.isOnboarded = user.isOnboarded();
    }
}
