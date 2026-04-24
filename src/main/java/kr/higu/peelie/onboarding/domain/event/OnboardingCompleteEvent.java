package kr.higu.peelie.onboarding.domain.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OnboardingCompleteEvent {
    private final String eventId;
    private final Long userId;
    private final String nickname;
    private final List<AnswerPayload> answers;
    private final LocalDateTime occurredAt;

    public static OnboardingCompleteEvent of(String eventId, Long userId, String nickname, List<AnswerPayload> answers) {
        return new OnboardingCompleteEvent(eventId, userId, nickname, answers, LocalDateTime.now());
    }

    @Getter
    @AllArgsConstructor
    public static class AnswerPayload {
        private final Long questionId;
        private final String purpose;
        private final String answer;
        private final String optionTag;
    }
}
