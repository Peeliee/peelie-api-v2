package kr.higu.peelie.onboarding.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class QuestionInfo {

    private final Long id;
    private final String content;
    private final String purpose;
    private final int displayOrder;
    private final Question.QuestionType questionType;
    private final List<AnswerOption> answerOptions;

    public QuestionInfo(Question question) {
        this.id = question.getId();
        this.content = question.getContent();
        this.purpose = question.getPurpose();
        this.displayOrder = question.getDisplayOrder();
        this.questionType = question.getQuestionType();
        this.answerOptions = question.getAnswerOptions();
    }
}
