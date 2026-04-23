package kr.higu.peelie.onboarding.domain;

import kr.higu.peelie.common.exception.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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

    @InjectMocks
    private OnboardingAdminServiceImpl onboardingAdminService;

    @Test
    @DisplayName("성공: 객관식 질문을 생성하면 선택지도 함께 저장한다")
    void registerChoiceQuestionWithAnswerOptions() {
        // given
        QuestionCommand.RegisterQuestionRequest command = QuestionCommand.RegisterQuestionRequest.builder()
                .content("질문")
                .purpose("목적")
                .displayOrder(1)
                .questionType(Question.QuestionType.CHOICE)
                .answerOptionsRequestList(java.util.List.of(
                        QuestionCommand.RegisterAnswerOptionRequest.builder()
                                .content("선택지 1")
                                .displayOrder(1)
                                .optionTag("tag1")
                                .build(),
                        QuestionCommand.RegisterAnswerOptionRequest.builder()
                                .content("선택지 2")
                                .displayOrder(2)
                                .optionTag("tag2")
                                .build()
                ))
                .build();
        ArgumentCaptor<Question> questionCaptor = ArgumentCaptor.forClass(Question.class);
        given(questionStore.store(questionCaptor.capture())).willAnswer(invocation -> invocation.getArgument(0));

        // when
        onboardingAdminService.registerQuestion(command);

        // then
        Question savedQuestion = questionCaptor.getValue();
        assertThat(savedQuestion.getQuestionType()).isEqualTo(Question.QuestionType.CHOICE);
        assertThat(savedQuestion.getAnswerOptions()).hasSize(2);
        assertThat(savedQuestion.getAnswerOptions().get(0).getQuestion()).isEqualTo(savedQuestion);
        assertThat(savedQuestion.getAnswerOptions().get(1).getQuestion()).isEqualTo(savedQuestion);
    }

    @Test
    @DisplayName("성공: 주관식 질문을 생성하면 선택지는 저장하지 않는다")
    void registerSubjectiveQuestionWithoutAnswerOptions() {
        // given
        QuestionCommand.RegisterQuestionRequest command = QuestionCommand.RegisterQuestionRequest.builder()
                .content("질문")
                .purpose("목적")
                .displayOrder(1)
                .questionType(Question.QuestionType.SUBJECTIVE)
                .answerOptionsRequestList(java.util.List.of(
                        QuestionCommand.RegisterAnswerOptionRequest.builder()
                                .content("선택지 1")
                                .displayOrder(1)
                                .optionTag("tag1")
                                .build()
                ))
                .build();
        ArgumentCaptor<Question> questionCaptor = ArgumentCaptor.forClass(Question.class);
        given(questionStore.store(questionCaptor.capture())).willAnswer(invocation -> invocation.getArgument(0));

        // when
        onboardingAdminService.registerQuestion(command);

        // then
        Question savedQuestion = questionCaptor.getValue();
        assertThat(savedQuestion.getQuestionType()).isEqualTo(Question.QuestionType.SUBJECTIVE);
        assertThat(savedQuestion.getAnswerOptions()).isEmpty();
    }

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
        question.addAnswerOption("선택지", 1, "tag");
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
