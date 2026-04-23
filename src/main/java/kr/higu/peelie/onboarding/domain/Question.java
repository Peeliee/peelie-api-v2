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

    @Column(nullable = false)
    private int displayOrder;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType;

    // Question : AnswerOption  = 1:N
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerOption> answerOptions = new ArrayList<>();

    @Builder
    public Question(String content, String purpose, int displayOrder, QuestionType questionType) {
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

    public void changeChoice() {
        this.questionType = QuestionType.CHOICE;
    }

    public void changeSubjective() {
        this.questionType = QuestionType.SUBJECTIVE;
    }

    public enum QuestionType {
        CHOICE,
        SUBJECTIVE;
    }
}
