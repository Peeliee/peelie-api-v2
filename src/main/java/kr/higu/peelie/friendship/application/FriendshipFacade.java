package kr.higu.peelie.friendship.application;

import kr.higu.peelie.friendship.domain.FriendCode;
import kr.higu.peelie.friendship.domain.FriendInfo;
import kr.higu.peelie.friendship.domain.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipFacade {

    private final FriendshipService friendshipService;

    public FriendCode getInviteCode(String userPublicId) {
        return friendshipService.getInviteCode(userPublicId);
    }

    public FriendInfo addFriend(String userPublicId, String code) {
        return friendshipService.addFriend(userPublicId, code);
    }

    public List<FriendInfo> getFriends(String userPublicId) {
        return friendshipService.getFriends(userPublicId);
    }
}
