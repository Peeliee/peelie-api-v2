package kr.higu.peelie.onboarding.domain;

import jakarta.persistence.*;
import kr.higu.peelie.common.exception.InvalidParamException;
import kr.higu.peelie.common.jpa.BaseTimeEntity;
import kr.higu.peelie.common.util.PublicIdGenerator;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Table(name = "onboarding_submissions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Onboarding extends BaseTimeEntity {
    private static final String PREFIX_EVENT = "event_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String eventId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String answer1;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String answer2;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String answer3;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String answer4;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String answer5;

    @Builder
    public Onboarding(Long userId, List<String> answers) {
        if (userId == null) throw new InvalidParamException("유저 ID가 입력되지 않았습니다.");

        if (answers == null || answers.size() != 5) {
            throw new InvalidParamException("onboarding answers size mismatch");
        }

        this.userId = userId;
        this.eventId = PublicIdGenerator.randomCharacterWithPrefix(PREFIX_EVENT);
        this.answer1 = answers.get(0);
        this.answer2 = answers.get(1);
        this.answer3 = answers.get(2);
        this.answer4 = answers.get(3);
        this.answer5 = answers.get(4);
    }
}
