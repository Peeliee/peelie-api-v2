package kr.higu.peelie.onboarding.interfaces.admin;

import kr.higu.peelie.onboarding.domain.AnswerOption;
import kr.higu.peelie.onboarding.domain.Question;
import kr.higu.peelie.onboarding.domain.QuestionCommand;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OnboardingAdminMapper {

    public QuestionCommand.RegisterQuestionRequest toRegisterCommand(OnboardingAdminForm.QuestionRequest form) {
        return QuestionCommand.RegisterQuestionRequest.builder()
                .content(form.getContent())
                .purpose(form.getPurpose())
                .displayOrder(form.getDisplayOrder())
                .questionType(form.getQuestionType())
                .answerOptionsRequestList(toAnswerOptionCommands(form.getAnswerOptions()))
                .build();
    }

    public QuestionCommand.UpdateQuestionRequest toUpdateCommand(
            Long questionId,
            OnboardingAdminForm.QuestionRequest form
    ) {
        return QuestionCommand.UpdateQuestionRequest.builder()
                .questionId(questionId)
                .content(form.getContent())
                .purpose(form.getPurpose())
                .displayOrder(form.getDisplayOrder())
                .questionType(form.getQuestionType())
                .build();
    }

    public List<OnboardingAdminView.QuestionItem> toQuestionItems(List<Question> questions) {
        return questions.stream()
                .map(this::toQuestionItem)
                .toList();
    }

    private List<QuestionCommand.RegisterAnswerOptionRequest> toAnswerOptionCommands(
            List<OnboardingAdminForm.RegisterAnswerOption> answerOptions
    ) {
        return answerOptions.stream()
                .filter(OnboardingAdminForm.RegisterAnswerOption::isComplete)
                .map(this::toAnswerOptionCommand)
                .toList();
    }

    private QuestionCommand.RegisterAnswerOptionRequest toAnswerOptionCommand(
            OnboardingAdminForm.RegisterAnswerOption answerOption
    ) {
        return QuestionCommand.RegisterAnswerOptionRequest.builder()
                .content(answerOption.getContent())
                .displayOrder(answerOption.getDisplayOrder())
                .optionTag(answerOption.getOptionTag())
                .build();
    }

    private OnboardingAdminView.QuestionItem toQuestionItem(Question question) {
        return new OnboardingAdminView.QuestionItem(
                question.getId(),
                question.getContent(),
                question.getPurpose(),
                question.getDisplayOrder(),
                question.getQuestionType(),
                question.getQuestionType() == Question.QuestionType.SUBJECTIVE,
                toAnswerOptionItems(question.getAnswerOptions())
        );
    }

    private List<OnboardingAdminView.AnswerOptionItem> toAnswerOptionItems(List<AnswerOption> answerOptions) {
        return answerOptions.stream()
                .map(this::toAnswerOptionItem)
                .toList();
    }

    private OnboardingAdminView.AnswerOptionItem toAnswerOptionItem(AnswerOption answerOption) {
        return new OnboardingAdminView.AnswerOptionItem(
                answerOption.getId(),
                answerOption.getContent(),
                answerOption.getDisplayOrder(),
                answerOption.getOptionTag()
        );
    }
}
