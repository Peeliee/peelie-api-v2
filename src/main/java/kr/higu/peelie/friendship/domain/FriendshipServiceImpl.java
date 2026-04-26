package kr.higu.peelie.friendship.domain;

import kr.higu.peelie.friendship.domain.exception.AlreadyFriendException;
import kr.higu.peelie.friendship.domain.exception.InvalidFriendInviteCodeException;
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
    private final FriendCodeManager friendCodeManager;

    @Override
    public FriendCode getInviteCode(String userPublicId) {
        Long userId = userReader.getUser(userPublicId).getId();
        FriendCode code = friendCodeManager.getOrCreate(userId);
        return code;
    }

    @Override
    @Transactional
    public FriendInfo addFriend(String userPublicId, String code) {
        Long userId = userReader.getUser(userPublicId).getId();
        Long friendId = friendCodeManager.findUserIdByCode(code)
                .orElseThrow(InvalidFriendInviteCodeException::new);
        User friend = userReader.getUser(friendId);

        if (friendshipReader.findFriendship(userId, friendId).isPresent()) {
            throw new AlreadyFriendException();
        }

        Friendship initFriendship = Friendship.create(userId, friendId);
        Friendship friendship = friendshipStore.store(initFriendship);

        return new FriendInfo(friend.getId(), friend.getUserPublicId(), friend.getNickname());
    }

    @Override
    public List<FriendInfo> getFriends(String userPublicId) {
        Long userId = userReader.getUser(userPublicId).getId();

        List<FriendInfo> friends = friendshipReader.getFriendShips(userId).stream()
                .map(friendship -> {
                    Long friendId = friendship.getPairUserId(userId);
                    User friendUser = userReader.getUser(friendId);
                    return new FriendInfo(friendUser.getId(), friendUser.getUserPublicId(), friendUser.getNickname());
                })
                .toList();

        return friends;
    }
}
