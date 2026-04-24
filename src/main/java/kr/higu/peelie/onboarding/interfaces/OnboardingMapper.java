package kr.higu.peelie.onboarding.interfaces;

import kr.higu.peelie.onboarding.domain.question.QuestionInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OnboardingMapper {

    public OnboardingResponse.Questions toQuestions(List<QuestionInfo> questions) {
        List<OnboardingResponse.Question> result = questions.stream()
                .map(this::toQuestion)
                .toList();
        return new OnboardingResponse.Questions(result);
    }

    public OnboardingResponse.Question toQuestion(QuestionInfo question) {
        List<OnboardingResponse.AnswerOption> result = question.getAnswerOptions().stream()
                .map(this::toAnswerOption)
                .toList();

        return new OnboardingResponse.Question(
                question.getId(),
                question.getContent(),
                question.getDisplayOrder(),
                question.getQuestionType().name(),
                result
        );
    }

    private OnboardingResponse.AnswerOption toAnswerOption(QuestionInfo.AnswerOptionInfo answerOption) {
        return new OnboardingResponse.AnswerOption(
                answerOption.getId(),
                answerOption.getContent(),
                answerOption.getDisplayOrder()
        );
    }
}
