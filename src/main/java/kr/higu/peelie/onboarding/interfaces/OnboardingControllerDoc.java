package kr.higu.peelie.onboarding.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.higu.peelie.common.response.CommonResponse;

@Tag(name = "2. Onboarding", description = "사용자 온보딩 API")
public interface OnboardingControllerDoc {

    @Operation(
            summary = "온보딩 질문 조회",
            description = "사용자 온보딩 화면 렌더링에 필요한 질문 목록을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "온보딩 질문 조회 성공",
                    content = @Content(schema = @Schema(implementation = OnboardingResponse.Questions.class))
            )
    })
    CommonResponse<OnboardingResponse.Questions> getQuestions();


    @Operation(
            summary = "온보딩 개별 질문 조회",
            description = "사용자 온보딩 질문을 조회합니다. 질문의 순서를 입력해서 n번 째 질문을 조회합니다"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "온보딩 질문 조회 성공",
                    content = @Content(schema = @Schema(implementation = OnboardingResponse.Question.class))
            )
    })
    CommonResponse<OnboardingResponse.Question> getQuestion(Integer order);

    @Operation(
            summary = "온보딩 완료",
            description = "사용자 온보딩 답변을 제출하고 완료 이벤트를 발행합니다.",
            requestBody = @RequestBody(
                    required = true,
                    description = "온보딩 완료 요청",
                    content = @Content(schema = @Schema(implementation = OnboardingRequest.Complete.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "온보딩이 완료되었습니다.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    CommonResponse<String> completeOnboarding(OnboardingRequest.Complete request);
}
