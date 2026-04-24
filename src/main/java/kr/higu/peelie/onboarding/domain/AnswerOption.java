package kr.higu.peelie.onboarding.domain;

import jakarta.persistence.*;
import kr.higu.peelie.common.exception.InvalidParamException;
import kr.higu.peelie.common.jpa.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "answer_options")
public class AnswerOption extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer displayOrder;

    // 해당 선택지를 통해 파악하는 사용자의 성향 정보 e.g) 사실, 상황에 먼저 반응하는 대화스타일, 주도형 ...
    @Column(name = "option_tag", nullable = false)
    private String optionTag;

    public AnswerOption(String content, Integer displayOrder, String optionTag) {
        changeContent(content);
        changeDisplayOrder(displayOrder);
        changeOptionTag(optionTag);
    }

    public void changeContent(String content) {
        if (content == null || content.isBlank()) throw new InvalidParamException("선택지의 내용이 없습니다.");
        this.content = content;
    }

    public void changeOptionTag(String optionTag) {
        if (optionTag == null || optionTag.isBlank()) throw new InvalidParamException("선택지의 태그 내용이 없습니다.");
        this.optionTag = optionTag;
    }

    public void changeDisplayOrder(Integer newDisplayOrder) {
        if (newDisplayOrder == null) throw new InvalidParamException("선택지의 순서가 정해지지 않았습니다");
        if (newDisplayOrder <= 0) throw new InvalidParamException("선택지의 순서는 1이상의 정수로 입력해주세요.");
        this.displayOrder = newDisplayOrder;
    }

    //어그리거트 루트 통해서만 사용되도록 default접근 제어
    void setQuestion(Question question) {
        this.question = question;
    }
}
