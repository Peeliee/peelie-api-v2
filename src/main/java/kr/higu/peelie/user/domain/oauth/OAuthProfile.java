package kr.higu.peelie.user.domain.oauth;

import lombok.Getter;

@Getter
public class OAuthProfile {

    private final String oid;
    private final String email;

    public OAuthProfile(String oid, String email) {
        this.oid = oid;
        this.email = email;
    }
}
