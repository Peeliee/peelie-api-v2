package kr.higu.peelie.user.domain;

import kr.higu.peelie.user.domain.oauth.Provider;
import lombok.Getter;

@Getter
public class UserInfo {
    private final Long id;
    private final String userPublicId;
    private final Provider provider;
    private final String oid;
    private final String email;
    private final String name;
    private final PersonalityType personalityType;
    private final Boolean isOnboarded;

    public UserInfo(User user) {
        this.id = user.getId();
        this.userPublicId = user.getUserPublicId();
        this.provider = user.getProvider();
        this.oid = user.getOid();
        this.email = user.getEmail();
        this.name = user.getName();
        this.personalityType = user.getPersonalityType();
        this.isOnboarded = user.getIsOnboarded();
    }
}
