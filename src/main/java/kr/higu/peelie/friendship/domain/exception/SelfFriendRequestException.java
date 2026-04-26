package kr.higu.peelie.friendship.domain.exception;

import kr.higu.peelie.common.exception.InvalidParamException;

public class SelfFriendRequestException extends InvalidParamException {
    public SelfFriendRequestException() {
        super("cannot add yourself as friend");
    }
}
