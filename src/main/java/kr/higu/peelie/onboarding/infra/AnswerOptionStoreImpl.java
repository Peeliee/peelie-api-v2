package kr.higu.peelie.onboarding.infra;

import kr.higu.peelie.onboarding.domain.AnswerOption;
import kr.higu.peelie.onboarding.domain.AnswerOptionStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AnswerOptionStoreImpl implements AnswerOptionStore {

    private final AnswerOptionRepository answerOptionRepository;

    @Override
    public AnswerOption store(AnswerOption answerOption) {
        return answerOptionRepository.save(answerOption);
    }

    @Override
    public List<AnswerOption> storeAll(List<AnswerOption> answerOptions) {
        return answerOptionRepository.saveAll(answerOptions);
    }
}
