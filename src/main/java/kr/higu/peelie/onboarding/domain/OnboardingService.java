package kr.higu.peelie.onboarding.domain;

import kr.higu.peelie.onboarding.domain.question.QuestionInfo;

import java.util.List;

public interface OnboardingService {
    QuestionInfo getQuestion(Integer displayOrder);
    List<QuestionInfo> getQuestions();
    void completeOnboarding(OnboardingCommand.Complete command, String userPublicId);
}
