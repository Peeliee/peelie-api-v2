package kr.higu.peelie.onboarding.domain.question;

public interface QuestionStore {
    Question store(Question question);

    void delete(Question question);
}
