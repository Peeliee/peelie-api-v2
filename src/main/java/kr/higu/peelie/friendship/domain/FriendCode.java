package kr.higu.peelie.friendship.domain;

import kr.higu.peelie.common.exception.InvalidParamException;
import lombok.Getter;

@Getter
public class FriendCode {
    private final Long userId;
    private final String code;

    public FriendCode(Long userId, String code) {
        if (userId == null || userId <= 0) {
            throw new InvalidParamException("유저 id가 유효하지 않습니다.");
        }
        if (code == null || code.isBlank()) {
            throw new InvalidParamException("코드값이 없습니다.");
        }
        this.userId = userId;
        this.code = code.trim();
    }
}
