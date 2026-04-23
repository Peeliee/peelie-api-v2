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

    @Override
    @Transactional
    public Question registerQuestion(QuestionCommand.RegisterQuestionRequest command) {
        Question question = command.toEntity();
        if (question.getQuestionType() == Question.QuestionType.CHOICE) {
            command.getAnswerOptionsRequestList().forEach(request ->
                    question.addAnswerOption(
                            request.getContent(),
                            request.getDisplayOrder(),
                            request.getOptionTag()
                    )
            );
        }
        return questionStore.store(question);
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
