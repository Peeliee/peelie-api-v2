package kr.higu.peelie.onboarding.domain;

import jakarta.persistence.*;
import kr.higu.peelie.common.exception.InvalidParamException;
import kr.higu.peelie.common.jpa.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "onboarding_questions")
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String purpose;

    @Column(nullable = false, unique = true)
    private Integer displayOrder;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType;

    // Question : AnswerOption  = 1:N
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerOption> answerOptions = new ArrayList<>();

    @Builder
    public Question(String content, String purpose, Integer displayOrder, QuestionType questionType) {
        this.content = content;
        this.purpose = purpose;
        this.displayOrder = displayOrder;
        this.questionType = questionType;
    }

    public void changeContent(String content) {
        if (content == null || content.isBlank()) throw new InvalidParamException("질문의 내용이 없습니다.");
        this.content = content;
    }

    public void changePurpose(String purpose) {
        if (purpose == null || purpose.isBlank()) throw new InvalidParamException("질문의 목적을 선택해주세요");
        this.purpose = purpose;
    }

    public void changeDisplayOrder(Integer newDisplayOrder) {
        if (newDisplayOrder == null) throw new InvalidParamException("질문의 순서를 지정하지 않았습니다.");
        if (newDisplayOrder < 0) throw new InvalidParamException("질문의 순서는 0이상의 정수여야합니다.");
        this.displayOrder = newDisplayOrder;
    }

    public void changeQuestionType(QuestionType newQuestionType) {
        if (newQuestionType == null) throw new InvalidParamException("질문 타입을 선택해주세요.");
        this.questionType = newQuestionType;
        if (newQuestionType == QuestionType.SUBJECTIVE) {
            answerOptions.clear();
        }
    }

    public void update(String content, String purpose, Integer displayOrder, QuestionType questionType) {
        changeContent(content);
        changePurpose(purpose);
        changeDisplayOrder(displayOrder);
        changeQuestionType(questionType);
    }

    public void addAnswerOption(String content, Integer displayOrder, String optionTag) {
        if (questionType == QuestionType.SUBJECTIVE) {
            throw new InvalidParamException("주관식 질문에는 선택지를 추가할 수 없습니다.");
        }

        AnswerOption answerOption = new AnswerOption(content, displayOrder, optionTag);
        answerOption.setQuestion(this);
        answerOptions.add(answerOption);
    }

    public enum QuestionType {
        CHOICE,
        SUBJECTIVE;
    }
}
