package kr.higu.peelie.onboarding.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class QuestionCommand {

    @Getter
    @Builder
    public static class RegisterQuestionRequest {
        private final String content;
        private final String purpose;
        private final Integer displayOrder;
        private final Question.QuestionType questionType;
        private final List<RegisterAnswerOptionRequest> answerOptionsRequestList;

        public Question toEntity() {
            return Question.builder()
                    .content(content)
                    .purpose(purpose)
                    .displayOrder(displayOrder)
                    .questionType(questionType)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class RegisterAnswerOptionRequest {
        private final String content;
        private final int displayOrder;
        private final String optionTag;

        public AnswerOption toEntity(Question question) {
            return AnswerOption.builder().
                    question(question)
                    .content(content)
                    .displayOrder(displayOrder)
                    .optionTag(optionTag)
                    .build();
        }
    }
}
