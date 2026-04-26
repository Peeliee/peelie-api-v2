package kr.higu.peelie.avatar.infra;

import kr.higu.peelie.avatar.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByChatRoomPublicId(String chatRoomPublicId);

    Optional<ChatRoom> findByUserIdAndFriendId(Long userId, Long friendId);

    @Query("""
            select cr
            from ChatRoom cr
            where cr.userId = :userId
            order by cr.lastMessageAt desc, cr.id desc
            """)
    List<ChatRoom> findAllByUserIdOrderByLastMessageAtDesc(@Param("userId") Long userId);
}
