package kr.higu.peelie.onboarding.interfaces.admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.higu.peelie.onboarding.domain.question.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class OnboardingAdminForm {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class QuestionRequest {
        @NotBlank
        private String content;

        @NotBlank
        private String purpose;

        @NotNull
        private Integer displayOrder;

        @NotNull
        private Question.QuestionType questionType;

        @Valid
        private List<RegisterAnswerOption> answerOptions = new ArrayList<>();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RegisterAnswerOption {
        private String content;
        private Integer displayOrder;
        private String optionTag;

        public boolean isComplete() {
            return content != null && !content.isBlank()
                    && displayOrder != null
                    && optionTag != null && !optionTag.isBlank();
        }
    }
}
