package kr.higu.peelie.user.domain;

import jakarta.persistence.*;
import kr.higu.peelie.common.exception.InvalidParamException;
import kr.higu.peelie.common.jpa.BaseTimeEntity;
import kr.higu.peelie.common.util.PublicIdGenerator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Entity
@Getter
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_user_provider_oid",
                columnNames = {"provider", "oid"}
        )
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    private static final String PREFIX_USER = "usr_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userPublicId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;

    @Column(nullable = false)
    private String oid;

    private String email;

    private String nickname;

    private boolean isOnboarded;

    @Builder
    public User(Provider provider, String oid, String email) {
        if (provider == null) throw new InvalidParamException("empty OAuth provider");
        if (oid == null || oid.isBlank()) throw new InvalidParamException("empty provider ID(oid)");
        if (email == null || email.isBlank()) throw new InvalidParamException("empty email");

        this.userPublicId = PublicIdGenerator.randomCharacterWithPrefix(PREFIX_USER);
        this.provider = provider;
        this.oid = oid;
        this.email = email;
    }

    public void changeNickname(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new InvalidParamException("nickname is empty");
        }
        this.nickname = nickname;
    }

    public void completeOnboarding(String nickname) {
        changeNickname(nickname);
        this.isOnboarded = true;
    }

    public enum Provider {
        KAKAO;

        public static Provider from(String value) {
            if (value == null || value.isBlank()) {
                throw new InvalidParamException("provider is required");
            }
            try {
                return Provider.valueOf(value.trim().toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException e) {
                throw new InvalidParamException("unsupported provider: " + value);
            }
        }
    }
}
