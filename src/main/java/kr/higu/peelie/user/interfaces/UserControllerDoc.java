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
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "1. User", description = "사용자 정보 API")
public interface UserControllerDoc {

    @Operation(summary = "웹 OAuth 로그인", description = "인가 코드를 이용해 로그인합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(schema = @Schema(implementation = UserResponse.Login.class))
            )
    })
    CommonResponse<UserResponse.Login> webLogin(
            @PathVariable String provider,
            @RequestBody @Valid UserRequest.WebLogin request
    );

    @Operation(summary = "네이티브 OAuth 로그인", description = "액세스 토큰을 이용해 로그인합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(schema = @Schema(implementation = UserResponse.Login.class))
            )
    })
    CommonResponse<UserResponse.Login> nativeLogin(
            @PathVariable String provider,
            @RequestBody @Valid UserRequest.NativeLogin request
    );


    @Operation(summary = "내 프로필 조회", description = "액세스 토큰을 이용해 사용자를 식별합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "프로필 조회 성공 응답",
                    content = @Content(schema = @Schema(implementation = UserResponse.User.class))
            )
    })
    CommonResponse<UserResponse.User> getMe();

    @Operation(summary = "내 프로필 수정", description = "엑세스 토큰을 이용해 사용자를 식별합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "내 프로필 수정 성공 응답",
                    content = @Content(schema = @Schema(implementation = UserResponse.User.class))
            )
    })
    CommonResponse<UserResponse.User> updateUser(
            @RequestBody UserRequest.UpdateUser request);

    @Operation(summary = "온보딩 완료", description = "액세스 토큰을 이용해 사용자를 식별합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "온보딩 완료 응답",
                    content = @Content(schema = @Schema(implementation = UserResponse.User.class))
            )
    })
    CommonResponse<UserResponse.User> completeOnboarding(
            @RequestBody UserRequest.Onboarding request);
}
