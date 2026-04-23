package kr.higu.peelie.onboarding.domain;

import kr.higu.peelie.common.exception.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class OnboardingAdminServiceImplTest {

    @Mock
    private QuestionReader questionReader;

    @Mock
    private QuestionStore questionStore;

    @Mock
    private AnswerOptionStore answerOptionStore;

    @InjectMocks
    private OnboardingAdminServiceImpl onboardingAdminService;

    @Test
    @DisplayName("성공: 질문을 수정한다")
    void updateQuestion() {
        // given
        Question question = Question.builder()
                .content("기존 질문")
                .purpose("기존 목적")
                .displayOrder(1)
                .questionType(Question.QuestionType.CHOICE)
                .build();
        question.getAnswerOptions().add(AnswerOption.builder()
                .question(question)
                .content("선택지")
                .displayOrder(1)
                .optionTag("tag")
                .build());
        QuestionCommand.UpdateQuestionRequest command = QuestionCommand.UpdateQuestionRequest.builder()
                .questionId(1L)
                .content("수정 질문")
                .purpose("수정 목적")
                .displayOrder(2)
                .questionType(Question.QuestionType.SUBJECTIVE)
                .build();
        given(questionReader.getQuestion(1L)).willReturn(question);

        // when
        onboardingAdminService.updateQuestion(command);

        // then
        assertThat(question.getContent()).isEqualTo("수정 질문");
        assertThat(question.getPurpose()).isEqualTo("수정 목적");
        assertThat(question.getDisplayOrder()).isEqualTo(2);
        assertThat(question.getQuestionType()).isEqualTo(Question.QuestionType.SUBJECTIVE);
        assertThat(question.getAnswerOptions()).isEmpty();
        then(questionStore).should().store(question);
    }

    @Test
    @DisplayName("실패: 존재하지 않는 질문은 수정할 수 없다")
    void updateQuestionFailsWhenQuestionDoesNotExist() {
        // given
        QuestionCommand.UpdateQuestionRequest command = QuestionCommand.UpdateQuestionRequest.builder()
                .questionId(1L)
                .content("수정 질문")
                .purpose("수정 목적")
                .displayOrder(2)
                .questionType(Question.QuestionType.CHOICE)
                .build();
        given(questionReader.getQuestion(1L)).willThrow(new EntityNotFoundException("해당 ID의 질문이 존재하지 않습니다."));

        // when // then
        assertThatThrownBy(() -> onboardingAdminService.updateQuestion(command))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("해당 ID의 질문이 존재하지 않습니다.");
    }
}
