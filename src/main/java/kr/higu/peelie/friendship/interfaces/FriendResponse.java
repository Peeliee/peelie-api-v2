package kr.higu.peelie.friendship.interfaces;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class FriendResponse {

    private FriendResponse() {
    }

    @Getter
    @AllArgsConstructor
    public static class InviteCode {
        private final String code;
    }

    @Getter
    @AllArgsConstructor
    public static class AddFriend {
        private final String friendPublicId;
        private final String nickname;
    }

    @Getter
    @AllArgsConstructor
    public static class FriendItem {
        private final String friendPublicId;
        private final String nickname;
    }

    @Getter
    @AllArgsConstructor
    public static class FriendList {
        private final List<FriendItem> friends;
    }

}
