package kr.higu.peelie.onboarding.domain.question;

import java.util.List;

public interface QuestionReader {
    Question getQuestion(Long questionId);
    Question getQuestionByDisplayOrder(Integer displayOrder);
    List<Question> getQuestions();
}
