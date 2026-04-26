package kr.higu.peelie.friendship.infra;

import kr.higu.peelie.friendship.domain.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Query("""
            select f
            from Friendship f
            where f.userId1 = :userId or f.userId2 = :userId
            order by f.id desc
            """)
    List<Friendship> findAllByUserId(@Param("userId") Long userId);

    Optional<Friendship> findByUserId1AndUserId2(Long userId1, Long userId2);
}
