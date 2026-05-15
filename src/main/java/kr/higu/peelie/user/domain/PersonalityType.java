package kr.higu.peelie.user.domain;

import kr.higu.peelie.common.exception.InvalidParamException;

import java.util.Locale;

public enum PersonalityType {
    STRAIGHT_SHOOTER,    // 직진 본능파
    ENERGETIC_TALKER,    // 불꽃토커
    QUIET_CHARMER,       // 조용한호감캐
    ANALYTICAL_OBSERVER, // 뇌풀가동 분석파
    HEART_COLLECTOR,     // 속마음 수집가
    STAGE_SETTER;        // 판깔기 전문가

    public static PersonalityType from(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidParamException("유저 타입값이 비어있습니다.");
        }
        try {
            return PersonalityType.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new InvalidParamException("존재하지 않는 유저 타입입니다.: " + value);
        }
    }
}