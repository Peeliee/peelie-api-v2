package kr.higu.peelie.friendship.infra;

import kr.higu.peelie.friendship.domain.Friendship;
import kr.higu.peelie.friendship.domain.FriendshipReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FriendshipReaderImpl implements FriendshipReader {

    private final FriendshipRepository friendshipRepository;

    @Override
    public List<Friendship> getFriendShips(Long userId) {
        return friendshipRepository.findAllByUserId(userId);
    }

    @Override
    public Optional<Friendship> findFriendship(Long userId, Long friendUserId) {
        Long userId1 = Math.min(userId, friendUserId);
        Long userId2 = Math.max(userId, friendUserId);
        return friendshipRepository.findByUserId1AndUserId2(userId1, userId2);
    }
}
