package kr.higu.peelie.avatar.domain;

import jakarta.persistence.*;
import kr.higu.peelie.common.jpa.BaseTimeEntity;
import kr.higu.peelie.common.util.PublicIdGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(
        name = "chat_rooms",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_chat_session_user_avatar_owner",
                        columnNames = {"user_id", "friend_id"}
                )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseTimeEntity {

    private static final String PREFIX_CHAT_ROOM = "chr_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String chatRoomPublicId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "friend_id", nullable = false)
    private Long friendId; // 아바타 주인의 아이디

    @Column(nullable = false)
    private LocalDateTime lastMessageAt;

    private ChatRoom(Long userId, Long friendId) {
        this.chatRoomPublicId = PublicIdGenerator.randomCharacterWithPrefix(PREFIX_CHAT_ROOM);
        this.userId = userId;
        this.friendId = friendId;
        this.lastMessageAt = LocalDateTime.now();
    }

    public static ChatRoom init(Long userId, Long friendId) {
        return new ChatRoom(userId, friendId);
    }

    public void touch() {
        this.lastMessageAt = LocalDateTime.now();
    }
}
