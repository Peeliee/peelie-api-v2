package kr.higu.peelie.user.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.higu.peelie.common.response.CommonResponse;

@Tag(
        name = "1. User",
        description = "유저 프로필 API. 로그인/토큰 발급은 0. Auth API 를 사용합니다."
)
public interface UserControllerDoc {

    @Operation(
            summary = "내 프로필 조회",
            description = """
                    액세스 토큰으로 현재 사용자를 식별해 내 프로필을 조회합니다.

                    응답 주요 필드:
                    - userPublicId: 클라이언트/다른 API 요청에서 사용하는 사용자 공개 식별자입니다.
                    - friendCode: 다른 사용자가 나를 친구로 추가할 때 입력하는 8자리 영구 코드입니다.
                    - personalityType: 아바타/채팅 컨텍스트에서 사용하는 사용자 성향입니다.
                    - isOnboarded: 온보딩 완료 여부입니다. 온보딩 API 는 유지 중이지만 신규 정책에서는 deprecated 로 취급합니다.
                    - createdAt: 사용자 생성 시각입니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "프로필 조회 성공. userPublicId, friendCode, name, personalityType, isOnboarded, createdAt 을 반환합니다.",
                    content = @Content(schema = @Schema(implementation = UserResponse.User.class))
            )
    })
    CommonResponse<UserResponse.User> getMe();

    @Operation(
            summary = "내 프로필 수정",
            description = """
                    액세스 토큰으로 현재 사용자를 식별해 내 프로필을 부분 수정합니다.

                    PATCH 정책:
                    - name 과 personalityType 은 모두 optional 입니다.
                    - 요청에 포함되지 않은 필드는 기존 값을 유지합니다.
                    - name 이 포함되면 1~20자여야 하며, 공백 문자열은 허용하지 않습니다.
                    - personalityType 이 포함되면 서버 enum 값과 정확히 일치해야 합니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "내 프로필 수정 성공. 수정 후 최신 프로필을 반환합니다.",
                    content = @Content(schema = @Schema(implementation = UserResponse.User.class))
            )
    })
    CommonResponse<UserResponse.User> updateUser(
            @RequestBody @Valid UserRequest.UpdateUser request);

    @Operation(
            summary = "온보딩 완료",
            description = """
                    액세스 토큰으로 현재 사용자를 식별해 온보딩 정보를 저장합니다.

                    현재 온보딩 플로우는 deprecated 로 취급하지만, 기존 클라이언트 호환을 위해 API 는 유지합니다.
                    - name 은 필수이며 1~20자입니다.
                    - personalityType 은 필수이며 서버 enum 값과 정확히 일치해야 합니다.
                    - 성공 시 isOnboarded 가 true 로 변경됩니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "온보딩 완료 성공. 저장 후 최신 프로필을 반환합니다.",
                    content = @Content(schema = @Schema(implementation = UserResponse.User.class))
            )
    })
    CommonResponse<UserResponse.User> completeOnboarding(
            @RequestBody UserRequest.Onboarding request);
}
