package kr.higu.peelie.onboarding.domain;

import kr.higu.peelie.common.exception.InvalidParamException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class QuestionTest {

    @Test
    @DisplayName("성공: 객관식 질문을 주관식으로 수정하면 선택지가 모두 삭제된다")
    void updateToSubjectiveClearsAnswerOptions() {
        // given
        Question question = Question.builder()
                .content("질문")
                .purpose("목적")
                .displayOrder(1)
                .questionType(Question.QuestionType.CHOICE)
                .build();
        question.addAnswerOption("선택지 1", 1, "tag1");
        question.addAnswerOption("선택지 2", 2, "tag2");

        // when
        question.update("수정 질문", "수정 목적", 3, Question.QuestionType.SUBJECTIVE);

        // then
        assertThat(question.getContent()).isEqualTo("수정 질문");
        assertThat(question.getPurpose()).isEqualTo("수정 목적");
        assertThat(question.getDisplayOrder()).isEqualTo(3);
        assertThat(question.getQuestionType()).isEqualTo(Question.QuestionType.SUBJECTIVE);
        assertThat(question.getAnswerOptions()).isEmpty();
    }

    @Test
    @DisplayName("성공: 주관식 질문을 객관식으로 수정해도 선택지는 자동 생성되지 않는다")
    void updateToChoiceDoesNotCreateAnswerOptions() {
        // given
        Question question = Question.builder()
                .content("질문")
                .purpose("목적")
                .displayOrder(1)
                .questionType(Question.QuestionType.SUBJECTIVE)
                .build();

        // when
        question.update("수정 질문", "수정 목적", 2, Question.QuestionType.CHOICE);

        // then
        assertThat(question.getQuestionType()).isEqualTo(Question.QuestionType.CHOICE);
        assertThat(question.getAnswerOptions()).isEmpty();
    }

    @Test
    @DisplayName("성공: 객관식 질문을 객관식으로 수정하면 기존 선택지는 유지된다")
    void updateChoiceQuestionKeepsAnswerOptions() {
        // given
        Question question = Question.builder()
                .content("질문")
                .purpose("목적")
                .displayOrder(1)
                .questionType(Question.QuestionType.CHOICE)
                .build();
        question.addAnswerOption("선택지 1", 1, "tag1");

        // when
        question.update("수정 질문", "수정 목적", 2, Question.QuestionType.CHOICE);

        // then
        assertThat(question.getQuestionType()).isEqualTo(Question.QuestionType.CHOICE);
        assertThat(question.getAnswerOptions()).hasSize(1);
        assertThat(question.getAnswerOptions().get(0).getContent()).isEqualTo("선택지 1");
    }

    @Test
    @DisplayName("성공: 선택지를 추가하면 질문과 연관관계가 함께 설정된다")
    void addAnswerOptionAssignsQuestion() {
        // given
        Question question = Question.builder()
                .content("질문")
                .purpose("목적")
                .displayOrder(1)
                .questionType(Question.QuestionType.CHOICE)
                .build();

        // when
        question.addAnswerOption("선택지 1", 1, "tag1");

        // then
        assertThat(question.getAnswerOptions()).hasSize(1);
        assertThat(question.getAnswerOptions().get(0).getQuestion()).isEqualTo(question);
    }

    @Test
    @DisplayName("실패: 주관식 질문에는 선택지를 추가할 수 없다")
    void addAnswerOptionFailsWhenSubjectiveQuestion() {
        // given
        Question question = Question.builder()
                .content("질문")
                .purpose("목적")
                .displayOrder(1)
                .questionType(Question.QuestionType.SUBJECTIVE)
                .build();

        // when // then
        assertThatThrownBy(() -> question.addAnswerOption("선택지 1", 1, "tag1"))
                .isInstanceOf(InvalidParamException.class)
                .hasMessage("주관식 질문에는 선택지를 추가할 수 없습니다.");
    }

    @Test
    @DisplayName("실패: 질문 타입 없이 수정할 수 없다")
    void updateFailsWhenQuestionTypeIsNull() {
        // given
        Question question = Question.builder()
                .content("질문")
                .purpose("목적")
                .displayOrder(1)
                .questionType(Question.QuestionType.CHOICE)
                .build();

        // when // then
        assertThatThrownBy(() -> question.update("수정 질문", "수정 목적", 2, null))
                .isInstanceOf(InvalidParamException.class)
                .hasMessage("질문 타입을 선택해주세요.");
    }
}
