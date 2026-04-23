package kr.higu.peelie.onboarding.interfaces;

import kr.higu.peelie.common.auth.UserContextHolder;
import kr.higu.peelie.common.response.CommonResponse;
import kr.higu.peelie.onboarding.application.OnboardingFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/onboarding")
public class OnboardingController {

    private final OnboardingFacade onboardingFacade;
    private final OnboardingMapper mapper;

    @GetMapping("/questions")
    public CommonResponse<OnboardingResponse.Questions> getQuestions() {
        UserContextHolder.requireUserContext();
        var questionInfos = onboardingFacade.getQuestions();
        var result = mapper.toQuestions(questionInfos);
        return CommonResponse.success(result);
    }

    @GetMapping("/questions/{order}")
    public CommonResponse<OnboardingResponse.Question> getQuestion(
            @PathVariable Integer order
    ) {
        UserContextHolder.requireUserContext();
        var questionInfo = onboardingFacade.getQuestion(order);
        var result = mapper.toQuestion(questionInfo);
        return CommonResponse.success(result);
    }
}
