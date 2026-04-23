package kr.higu.peelie.onboarding.application;

import kr.higu.peelie.onboarding.domain.OnboardingAdminService;
import kr.higu.peelie.onboarding.domain.Question;
import kr.higu.peelie.onboarding.domain.QuestionCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OnboardingAdminFacade {

    private final OnboardingAdminService onboardingAdminService;

    public void registerQuestion(QuestionCommand.RegisterQuestionRequest command) {
        onboardingAdminService.registerQuestion(command);
    }

    public List<Question> getQuestions() {
        return onboardingAdminService.getQuestions();
    }

    public void deleteQuestion(Long questionId) {
        onboardingAdminService.deleteQuestion(questionId);
    }

    public void changeChoice(Long questionId) {
        onboardingAdminService.changeChoice(questionId);
    }

    public void changeSubjective(Long questionId) {
        onboardingAdminService.changeSubjective(questionId);
    }
}
