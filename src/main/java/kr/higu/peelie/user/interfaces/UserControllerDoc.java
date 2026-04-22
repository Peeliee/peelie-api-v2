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

    @Operation(
            summary = "내 닉네임 수정",
            description = "로그인한 사용자의 닉네임을 수정합니다.",
            requestBody = @RequestBody(
                    required = true,
                    description = "닉네임 수정 요청",
                    content = @Content(schema = @Schema(implementation = UserRequest.Nickname.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "닉네임 수정 성공"
            )
    })
    CommonResponse<String> changeNickname(
            @RequestBody @Valid UserRequest.Nickname request
    );
}
