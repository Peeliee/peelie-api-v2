package kr.higu.peelie.onboarding.interfaces;

import jakarta.validation.Valid;
import kr.higu.peelie.common.auth.UserContextHolder;
import kr.higu.peelie.common.response.CommonResponse;
import kr.higu.peelie.onboarding.application.OnboardingFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public CommonResponse<String> completeOnboarding(
            @RequestBody @Valid OnboardingRequest.Complete request
    ) {
        String userPublicId = UserContextHolder.requireUserContext();
        onboardingFacade.completeOnboarding(request.toCommand(), userPublicId);
        return CommonResponse.success("온보딩이 완료되었습니다.");
    }
}
