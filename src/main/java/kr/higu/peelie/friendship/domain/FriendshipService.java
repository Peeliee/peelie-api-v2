package kr.higu.peelie.friendship.domain;

import java.util.List;

public interface FriendshipService {
    FriendCode getInviteCode(String userPublicId);
    // userPublicId는 현재 로그인한 사용자의 퍼블릭 id
    FriendInfo addFriend(String userPublicId, String code);
    List<FriendInfo> getFriends(String userPublicId);
}
