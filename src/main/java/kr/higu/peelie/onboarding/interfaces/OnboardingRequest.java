package kr.higu.peelie.onboarding.interfaces;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import kr.higu.peelie.onboarding.domain.OnboardingCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class OnboardingRequest {

    @Getter
    @NoArgsConstructor
    public static class Complete {
        @Valid
        @NotEmpty
        private List<Answer> answers;

        public OnboardingCommand.Complete toCommand() {
            List<OnboardingCommand.Answer> commandAnswers = answers.stream()
                    .map(Answer::toCommand)
                    .toList();

            return new OnboardingCommand.Complete(commandAnswers);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Answer {
        @NotNull
        private Long questionId;

        private Long answerOptionId;
        private String answerText;

        public OnboardingCommand.Answer toCommand() {
            return new OnboardingCommand.Answer(
                    questionId,
                    answerOptionId,
                    answerText
            );
        }
    }
}
