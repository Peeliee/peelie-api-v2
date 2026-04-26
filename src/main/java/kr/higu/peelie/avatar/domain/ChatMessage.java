package kr.higu.peelie.avatar.domain;

import jakarta.persistence.*;
import kr.higu.peelie.common.jpa.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat_message")
public class ChatMessage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatRoom chatRoom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ChatRole role;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private ChatMessage(ChatRoom chatRoom, ChatRole role, String content) {
        this.chatRoom = chatRoom;
        this.role = role;
        this.content = content;
    }

    public static ChatMessage of(ChatRoom chatRoom, ChatRole role, String content) {
        return new ChatMessage(chatRoom, role, content);
    }

    public enum ChatRole {
        USER,
        AVATAR
    }
}
