package kr.higu.peelie.onboarding.domain.question;

import java.util.List;

public interface QuestionService {

    Question registerQuestion(QuestionCommand.RegisterQuestionRequest command);
    void updateQuestion(QuestionCommand.UpdateQuestionRequest command);
    List<Question> getQuestions();
    void deleteQuestion(Long questionId);
}
