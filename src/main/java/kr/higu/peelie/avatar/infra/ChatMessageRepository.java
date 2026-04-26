package kr.higu.peelie.avatar.infra;

import kr.higu.peelie.avatar.domain.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByChatRoomIdOrderByIdDesc(Long chatRoomId, Pageable pageable);

    List<ChatMessage> findByChatRoomIdOrderByCreatedAtAscIdAsc(Long chatRoomId);

    @Query("""
            select cm
            from ChatMessage cm
            where cm.chatRoom.id in :chatRoomIds
              and cm.id in (
                    select max(innerCm.id)
                    from ChatMessage innerCm
                    where innerCm.chatRoom.id in :chatRoomIds
                    group by innerCm.chatRoom.id
              )
            """)
    List<ChatMessage> findLatestMessagesByChatRoomIds(@Param("chatRoomIds") List<Long> chatRoomIds);

    void deleteByChatRoomId(Long chatRoomId);
}
