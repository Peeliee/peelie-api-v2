package kr.higu.peelie.onboarding.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class OnboardingCommand {

    @Getter
    @AllArgsConstructor
    public static class Complete {
        private final List<Answer> answers;
    }

    @Getter
    @AllArgsConstructor
    public static class Answer {
        private final Long questionId;
        private final Long answerOptionId; //객관식 답변
        private final String answerText;   //서술형 답변
    }
}
