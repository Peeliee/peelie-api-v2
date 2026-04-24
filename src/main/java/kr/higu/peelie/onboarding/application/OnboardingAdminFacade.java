package kr.higu.peelie.onboarding.application;

import kr.higu.peelie.onboarding.domain.question.QuestionService;
import kr.higu.peelie.onboarding.domain.question.Question;
import kr.higu.peelie.onboarding.domain.question.QuestionCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OnboardingAdminFacade {

    private final QuestionService questionService;

    public void registerQuestion(QuestionCommand.RegisterQuestionRequest command) {
        questionService.registerQuestion(command);
    }

    public void updateQuestion(QuestionCommand.UpdateQuestionRequest command) {
        questionService.updateQuestion(command);
    }

    public List<Question> getQuestions() {
        return questionService.getQuestions();
    }

    public void deleteQuestion(Long questionId) {
        questionService.deleteQuestion(questionId);
    }
}
