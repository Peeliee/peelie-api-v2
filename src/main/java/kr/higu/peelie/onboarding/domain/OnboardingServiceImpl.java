package kr.higu.peelie.onboarding.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OnboardingServiceImpl implements OnboardingService {

    private final QuestionReader questionReader;

    @Override
    public QuestionInfo getQuestion(Integer displayOrder) {
        Question question = questionReader.getQuestionByDisplayOrder(displayOrder);
        return new QuestionInfo(question);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionInfo> getQuestions() {
        return questionReader.getQuestions().stream()
                .map(QuestionInfo::new)
                .toList();
    }
}

