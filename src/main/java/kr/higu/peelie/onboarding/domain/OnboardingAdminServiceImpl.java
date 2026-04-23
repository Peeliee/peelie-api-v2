package kr.higu.peelie.onboarding.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OnboardingAdminServiceImpl implements OnboardingAdminService {

    private final QuestionReader questionReader;
    private final QuestionStore questionStore;
    private final AnswerOptionStore answerOptionStore;

    @Override
    @Transactional
    public Question registerQuestion(QuestionCommand.RegisterQuestionRequest command) {

        // 온보징 질문 생성 및 저장
        Question initQuestion = command.toEntity();
        Question question = questionStore.store(initQuestion);

        // 주관신 문항이면 바로 리턴
        if (question.getQuestionType() == Question.QuestionType.SUBJECTIVE) {
            return question;
        }

        // 선택지 저장
        List<AnswerOption> initAnswerOptions = command.getAnswerOptionsRequestList().stream()
                .map(req -> req.toEntity(question))
                .toList();

        answerOptionStore.storeAll(initAnswerOptions);

        return question;
    }

    @Override
    @Transactional
    public void updateQuestion(QuestionCommand.UpdateQuestionRequest command) {
        Question question = questionReader.getQuestion(command.getQuestionId());
        question.update(
                command.getContent(),
                command.getPurpose(),
                command.getDisplayOrder(),
                command.getQuestionType()
        );
        questionStore.store(question);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> getQuestions() {
        return questionReader.getQuestions();
    }

    @Override
    @Transactional
    public void deleteQuestion(Long questionId) {
        Question question = questionReader.getQuestion(questionId);
        questionStore.delete(question);
    }
}
