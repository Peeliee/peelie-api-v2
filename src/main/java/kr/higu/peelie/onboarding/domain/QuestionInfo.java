package kr.higu.peelie.onboarding.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class QuestionInfo {

    private final Long id;
    private final String content;
    private final String purpose;
    private final Integer displayOrder;
    private final Question.QuestionType questionType;
    private final List<AnswerOptionInfo> answerOptions;

    public QuestionInfo(Question question) {
        this.id = question.getId();
        this.content = question.getContent();
        this.purpose = question.getPurpose();
        this.displayOrder = question.getDisplayOrder();
        this.questionType = question.getQuestionType();
        this.answerOptions = question.getAnswerOptions().stream()
                .map(AnswerOptionInfo::new)
                .toList();
    }

    @Getter
    public static class AnswerOptionInfo {
        private final Long id;
        private final Long questionId;
        private final String content;
        private final Integer displayOrder;
        private final String optionTag;

        public AnswerOptionInfo(AnswerOption answerOption) {
            this.id = answerOption.getId();
            this.questionId = answerOption.getQuestion().getId();
            this.content = answerOption.getContent();
            this.displayOrder = answerOption.getDisplayOrder();
            this.optionTag = answerOption.getOptionTag();
        }
    }
}
