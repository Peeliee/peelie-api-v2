package kr.higu.peelie.friendship.domain.exception;

import kr.higu.peelie.common.exception.InvalidParamException;

public class InvalidFriendRequestException extends InvalidParamException {
    public InvalidFriendRequestException(String message) {
        super(message);
    }
}
