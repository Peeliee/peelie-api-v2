package kr.higu.peelie.friendship.infra;

import kr.higu.peelie.friendship.domain.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    List<Friendship> findAllByOwnerIdOrderByCreatedAtDesc(Long ownerId);

    Optional<Friendship> findByOwnerIdAndFriendUserId(Long ownerId, Long friendUserId);
}
