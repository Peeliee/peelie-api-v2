package kr.higu.peelie.onboarding.infra;

import kr.higu.peelie.onboarding.domain.question.Question;
import kr.higu.peelie.onboarding.domain.question.QuestionStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionStoreImpl implements QuestionStore {

    private final QuestionRepository questionRepository;

    @Override
    public Question store(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void delete(Question question) {
        questionRepository.delete(question);
    }
}
