package kr.higu.peelie.friendship.domain;

import jakarta.persistence.*;
import kr.higu.peelie.common.jpa.BaseTimeEntity;
import kr.higu.peelie.friendship.domain.exception.InvalidFriendRequestException;
import kr.higu.peelie.friendship.domain.exception.SelfFriendRequestException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "friendships",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_friendship_user_pair", columnNames = {"user_id1", "user_id2"})
        }
)
public class Friendship extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId1;

    @Column(nullable = false)
    private Long userId2;

    private Friendship(Long userId1, Long userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
    }

    public static Friendship create(Long userId, Long friendUserId) {
        if (userId == null || userId <= 0) {
            throw new InvalidFriendRequestException("유저 Id가 유효하지 않습니다.");
        }
        if (friendUserId == null || friendUserId <= 0) {
            throw new InvalidFriendRequestException("친구의 Id가 유효하지 않습니다.");
        }
        if (userId.equals(friendUserId)) {
            throw new SelfFriendRequestException();
        }

        Long firstUserId = Math.min(userId, friendUserId);
        Long secondUserId = Math.max(userId, friendUserId);
        return new Friendship(firstUserId, secondUserId);
    }

    public Long getPairUserId(Long userId) {
        if (!userId1.equals(userId) && !userId2.equals(userId)) {
            throw new InvalidFriendRequestException("친구가 아닙니다.");
        }
        return userId1.equals(userId) ? userId2 : userId1;
    }
}
