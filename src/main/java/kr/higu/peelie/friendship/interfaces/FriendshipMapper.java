package kr.higu.peelie.friendship.interfaces;

import kr.higu.peelie.friendship.domain.FriendCode;
import kr.higu.peelie.friendship.domain.FriendInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FriendshipMapper {

    public FriendResponse.InviteCode toInviteCode(FriendCode friendCode) {
        return new FriendResponse.InviteCode(friendCode.getCode());
    }

    public FriendResponse.AddFriend toAddFriend(FriendInfo friendInfo) {
        return new FriendResponse.AddFriend(friendInfo.getFriendPublic(), friendInfo.getNickname());
    }

    public FriendResponse.FriendList toFriendList(List<FriendInfo> friends) {
        return new FriendResponse.FriendList(
                friends.stream()
                        .map(this::toFriendItem)
                        .toList()
        );
    }

    private FriendResponse.FriendItem toFriendItem(FriendInfo friendInfo) {
        return new FriendResponse.FriendItem(friendInfo.getFriendPublic(), friendInfo.getNickname());
    }
}
