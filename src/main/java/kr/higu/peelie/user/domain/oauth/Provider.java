package kr.higu.peelie.user.domain.oauth;

import kr.higu.peelie.common.exception.InvalidParamException;

import java.util.Locale;

public enum Provider {
    KAKAO;

    public static Provider from(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidParamException("provider is required");
        }
        try {
            return Provider.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new InvalidParamException("unsupported provider: " + value);
        }
    }
}
