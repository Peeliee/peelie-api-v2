package kr.higu.peelie.onboarding.domain;

import java.util.List;

public interface OnboardingAdminService {

    Question registerQuestion(QuestionCommand.RegisterQuestionRequest command);
    void updateQuestion(QuestionCommand.UpdateQuestionRequest command);
    List<Question> getQuestions();
    void deleteQuestion(Long questionId);
}
