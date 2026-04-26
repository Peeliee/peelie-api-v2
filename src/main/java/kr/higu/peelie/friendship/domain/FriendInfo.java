package kr.higu.peelie.friendship.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendInfo {
    private final Long friendId;
    private final String friendPublic;
    private final String nickname;
}
