package kr.higu.peelie.onboarding.domain;

import kr.higu.peelie.common.exception.InvalidParamException;
import kr.higu.peelie.onboarding.domain.question.Question;
import kr.higu.peelie.onboarding.domain.question.QuestionInfo;
import kr.higu.peelie.onboarding.domain.question.QuestionReader;
import kr.higu.peelie.user.domain.User;
import kr.higu.peelie.user.domain.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OnboardingServiceImpl implements OnboardingService {
    // 현재 서비스에서는 질문이 총 5가지로 구성되어있음
    private static final int REQUIRED_ANSWER_SIZE = 5;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final UserReader userReader;
    private final QuestionReader questionReader;
    private final OnboardingStore onboardingStore;

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

    @Override
    @Transactional
    public void completeOnboarding(OnboardingCommand.Complete command, String userPublicId) {
        User user = userReader.getUser(userPublicId);

        List<Question> questions = questionReader.getQuestions();
        List<String> answers = command.getAnswers();

        if (questions == null || questions.size() != REQUIRED_ANSWER_SIZE) {
            throw new InvalidParamException("onboarding questions size mismatch");
        }

        if (answers == null || answers.size() != REQUIRED_ANSWER_SIZE) {
            throw new InvalidParamException("onboarding answers size mismatch");
        }

        Onboarding initOnboarding = new Onboarding(user.getId(), answers);
        Onboarding onboarding = onboardingStore.store(initOnboarding);

//        applicationEventPublisher.publishEvent(
//                OnboardingCompleteEvent.of(onboarding.getEventId(), user.getId(), user.getNickname(), questions, answers)
//        );
    }
}

