package kr.higu.peelie.friendship.infra;

import kr.higu.peelie.friendship.domain.Friendship;
import kr.higu.peelie.friendship.domain.FriendshipStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendshipStoreImpl implements FriendshipStore {

    private final FriendshipRepository friendshipRepository;

    @Override
    public Friendship store(Friendship friendship) {
        return friendshipRepository.save(friendship);
    }
}
