package kr.higu.peelie.friendship.domain;

import kr.higu.peelie.friendship.domain.exception.AlreadyFriendException;
import kr.higu.peelie.user.domain.User;
import kr.higu.peelie.user.domain.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService{

    private final UserReader userReader;

    private final FriendshipReader friendshipReader;
    private final FriendshipStore friendshipStore;

    @Override
    public FriendCode getInviteCode(String userPublicId) {
        User user = userReader.getUser(userPublicId);
        return new FriendCode(user.getId(), user.getFriendCode());
    }

    @Override
    @Transactional
    public FriendInfo addFriend(String userPublicId, String friendCode) {
        Long userId = userReader.getUser(userPublicId).getId();
        User friend = userReader.getUserByFriendCode(friendCode);
        Long friendId = friend.getId();

        if (friendshipReader.findFriendship(userId, friendId).isPresent()) {
            throw new AlreadyFriendException();
        }

        Friendship initFriendship = Friendship.create(userId, friendId);
        Friendship friendship = friendshipStore.store(initFriendship);

        return new FriendInfo(friend.getId(), friend.getUserPublicId(), friend.getName(), friend.getPersonalityType());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FriendInfo> getFriends(String userPublicId) {
        Long userId = userReader.getUser(userPublicId).getId();

        List<FriendInfo> friends = friendshipReader.getFriendShips(userId).stream()
                .map(friendship -> {
                    Long friendId = friendship.getFriendUserId();
                    User friendUser = userReader.getUser(friendId);
                    return new FriendInfo(
                            friendUser.getId(),
                            friendUser.getUserPublicId(),
                            friendUser.getName(),
                            friendUser.getPersonalityType()
                    );
                })
                .toList();

        return friends;
    }
}
