package kr.higu.peelie.onboarding.application;

import kr.higu.peelie.onboarding.domain.OnboardingService;
import kr.higu.peelie.onboarding.domain.QuestionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OnboardingFacade {

    private final OnboardingService onboardingService;

    public QuestionInfo getQuestion(Integer displayOrder) {
        return onboardingService.getQuestion(displayOrder);
    }

    public List<QuestionInfo> getQuestions() {
        return onboardingService.getQuestions();
    }
}
