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
                @UniqueConstraint(name = "uk_friendship_owner_friend", columnNames = {"owner_id", "friend_user_id"})
        }
)
public class Friendship extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private Long friendUserId;

    private Friendship(Long ownerId, Long friendUserId) {
        this.ownerId = ownerId;
        this.friendUserId = friendUserId;
    }

    public static Friendship create(Long ownerId, Long friendUserId) {
        if (ownerId == null || ownerId <= 0) {
            throw new InvalidFriendRequestException("유저 Id가 유효하지 않습니다.");
        }
        if (friendUserId == null || friendUserId <= 0) {
            throw new InvalidFriendRequestException("친구의 Id가 유효하지 않습니다.");
        }
        if (ownerId.equals(friendUserId)) {
            throw new SelfFriendRequestException();
        }

        return new Friendship(ownerId, friendUserId);
    }
}
