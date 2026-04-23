package kr.higu.peelie.onboarding.interfaces;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class OnboardingResponse {

    @Getter
    @AllArgsConstructor
    @Schema(name = "AllQuestionsResponse")
    public static class Questions {
        private final List<Question> questions;
    }

    @Getter
    @AllArgsConstructor
    @Schema(name = "QuestionResponse")
    public static class Question {
        private final Long id;
        private final String content;
        private final Integer displayOrder;
        private final String questionType;
        private final List<AnswerOption> answerOptions;
    }

    @Getter
    @AllArgsConstructor
    @Schema(name = "AnswerOptionResponse")
    public static class AnswerOption {
        private final Long id;
        private final String content;
        private final int displayOrder;
    }
}
