package kr.higu.peelie.onboarding.domain;

import kr.higu.peelie.common.exception.InvalidParamException;
import kr.higu.peelie.onboarding.domain.event.OnboardingCompleteEvent;
import kr.higu.peelie.onboarding.domain.question.AnswerOption;
import kr.higu.peelie.onboarding.domain.question.Question;
import kr.higu.peelie.onboarding.domain.question.QuestionInfo;
import kr.higu.peelie.onboarding.domain.question.QuestionReader;
import kr.higu.peelie.user.domain.User;
import kr.higu.peelie.user.domain.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        List<OnboardingCommand.Answer> answers = command.getAnswers();

        List<OnboardingCompleteEvent.AnswerPayload> payloads = new ArrayList<>();
        List<String> rawAnswers = new ArrayList<>();

        for (var commandAnswer : answers) {
            Question question = questionReader.getQuestion(commandAnswer.getQuestionId());

            if (question.getQuestionType() == Question.QuestionType.CHOICE) {
                payloads.add(processChoiceAnswer(question, commandAnswer, rawAnswers));
                continue;
            }

            payloads.add(processSubjectiveAnswer(question, commandAnswer, rawAnswers));
        }

        Onboarding initOnboarding = new Onboarding(user.getId(), rawAnswers);
        Onboarding onboarding = onboardingStore.store(initOnboarding);

        applicationEventPublisher.publishEvent(
                OnboardingCompleteEvent.of(onboarding.getEventId(), user.getId(), user.getNickname(), payloads)
        );
    }

    private OnboardingCompleteEvent.AnswerPayload processChoiceAnswer(
            Question question,
            OnboardingCommand.Answer commandAnswer,
            List<String> rawAnswers
    ) {
        AnswerOption selectedOption = question.getAnswerOptions().stream()
                .filter(option -> option.getId().equals(commandAnswer.getAnswerOptionId()))
                .findFirst()
                .orElseThrow(() -> new InvalidParamException("선택지가 해당 질문에 존재하지 않습니다."));

        String rawAnswer = selectedOption.getContent();
        rawAnswers.add(rawAnswer);

        return new OnboardingCompleteEvent.AnswerPayload(question.getId(), question.getPurpose(), rawAnswer, selectedOption.getOptionTag());
    }

    private OnboardingCompleteEvent.AnswerPayload processSubjectiveAnswer(
            Question question, OnboardingCommand.Answer commandAnswer, List<String> rawAnswers
    ) {
        String rawAnswer = commandAnswer.getAnswerText();
        rawAnswers.add(rawAnswer);

        return new OnboardingCompleteEvent.AnswerPayload(question.getId(), question.getPurpose(), rawAnswer, null);
    }
}

