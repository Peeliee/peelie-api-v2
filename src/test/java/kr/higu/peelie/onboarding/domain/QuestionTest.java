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
        question.getAnswerOptions().add(AnswerOption.builder()
                .question(question)
                .content("선택지 1")
                .displayOrder(1)
                .optionTag("tag1")
                .build());
        question.getAnswerOptions().add(AnswerOption.builder()
                .question(question)
                .content("선택지 2")
                .displayOrder(2)
                .optionTag("tag2")
                .build());

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
        AnswerOption answerOption = AnswerOption.builder()
                .question(question)
                .content("선택지 1")
                .displayOrder(1)
                .optionTag("tag1")
                .build();
        question.getAnswerOptions().add(answerOption);

        // when
        question.update("수정 질문", "수정 목적", 2, Question.QuestionType.CHOICE);

        // then
        assertThat(question.getQuestionType()).isEqualTo(Question.QuestionType.CHOICE);
        assertThat(question.getAnswerOptions()).containsExactly(answerOption);
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
