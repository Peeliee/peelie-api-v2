package kr.higu.peelie.onboarding.domain;

import java.util.List;

public interface OnboardingAdminService {

    Question registerQuestion(QuestionCommand.RegisterQuestionRequest command);
    List<Question> getQuestions();
    void deleteQuestion(Long questionId);
    void changeChoice(Long questionId);
    void changeSubjective(Long questionId);
}
