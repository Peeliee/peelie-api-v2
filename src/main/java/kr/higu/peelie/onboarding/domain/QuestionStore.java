package kr.higu.peelie.onboarding.domain;

public interface QuestionStore {
    Question store(Question question);

    void delete(Question question);
}
