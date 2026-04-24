package kr.higu.peelie.onboarding.infra;

import kr.higu.peelie.common.exception.EntityNotFoundException;
import kr.higu.peelie.onboarding.domain.question.Question;
import kr.higu.peelie.onboarding.domain.question.QuestionReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionReaderImpl implements QuestionReader {

    private final QuestionRepository questionRepository;

    @Override
    public Question getQuestion(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 질문이 존재하지 않습니다."));
    }

    @Override
    public Question getQuestionByDisplayOrder(Integer displayId) {
        return questionRepository.findByDisplayOrder(displayId)
                .orElseThrow(() -> new EntityNotFoundException("해당 순서의 질문이 존재하지 않습니다."));
    }

    @Override
    public List<Question> getQuestions() {
        return questionRepository.findAllByOrderByDisplayOrderAsc();
    }
}
