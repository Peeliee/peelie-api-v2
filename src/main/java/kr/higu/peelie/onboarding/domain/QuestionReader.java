package kr.higu.peelie.onboarding.domain;

import java.util.List;

public interface QuestionReader {
    Question getQuestion(Long questionId);
    List<Question> getQuestions();
}
