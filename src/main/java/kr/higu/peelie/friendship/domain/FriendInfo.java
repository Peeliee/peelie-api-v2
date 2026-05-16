package kr.higu.peelie.friendship.domain;

import kr.higu.peelie.user.domain.PersonalityType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendInfo {
    private final Long friendId;
    private final String friendPublicId;
    private final String name;
    private final PersonalityType personalityType;
}
