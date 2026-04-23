package kr.higu.peelie.onboarding.domain;

import java.util.List;

public interface OnboardingService {
    QuestionInfo getQuestion(Integer displayOrder);
    List<QuestionInfo> getQuestions();
//    void completeOnboarding();
}
