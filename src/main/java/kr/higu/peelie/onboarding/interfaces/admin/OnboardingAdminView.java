package kr.higu.peelie.onboarding.interfaces.admin;

import kr.higu.peelie.onboarding.domain.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class OnboardingAdminView {

    @Getter
    @AllArgsConstructor
    public static class QuestionItem {
        private final Long id;
        private final String content;
        private final String purpose;
        private final int displayOrder;
        private final Question.QuestionType questionType;
        private final boolean subjective;
        private final List<AnswerOptionItem> answerOptions;
    }

    @Getter
    @AllArgsConstructor
    public static class AnswerOptionItem {
        private final Long id;
        private final String content;
        private final int displayOrder;
        private final String optionTag;
    }
}
