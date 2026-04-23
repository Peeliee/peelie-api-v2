package kr.higu.peelie.onboarding.domain;

import java.util.List;

public interface AnswerOptionStore {
    AnswerOption store(AnswerOption answerOption);
    List<AnswerOption> storeAll(List<AnswerOption> answerOptions);
}
