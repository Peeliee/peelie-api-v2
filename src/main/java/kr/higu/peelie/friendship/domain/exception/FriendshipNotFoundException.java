package kr.higu.peelie.friendship.domain.exception;

import kr.higu.peelie.common.exception.EntityNotFoundException;

public class FriendshipNotFoundException extends EntityNotFoundException {
    public FriendshipNotFoundException() {
        super("friendship not found");
    }
}
