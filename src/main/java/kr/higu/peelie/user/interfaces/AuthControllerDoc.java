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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(
        name = "0. Auth",
        description = "인증 API."
)
public interface AuthControllerDoc {

    @Operation(
            summary = "웹 OAuth 로그인",
            description = """
                    OAuth provider 의 authorization code 를 이용해 로그인합니다.

                    - provider path 값은 현재 KAKAO만 사용합니다.
                    - 성공 시 accessToken, refreshToken 을 반환합니다.
                    - 현재 Spring 구현은 신규 유저도 로그인 시점에 생성합니다.
                    - 로그인 이후 내 프로필은 GET /api/v1/users/me 에서 조회합니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공. accessToken 과 refreshToken 을 반환합니다.",
                    content = @Content(schema = @Schema(implementation = UserResponse.Login.class))
            )
    })
    CommonResponse<UserResponse.Login> webLogin(
            @PathVariable String provider,
            @RequestBody @Valid UserRequest.WebLogin request
    );

    @Operation(
            summary = "네이티브 OAuth 로그인",
            description = """
                    앱/네이티브 클라이언트에서 받은 OAuth provider accessToken 으로 로그인합니다.

                    - provider path 값은 현재 KAKAO 등을 사용합니다.
                    - 성공 시 accessToken, refreshToken 을 반환합니다.
                    - 현재 Spring 구현은 신규 유저도 로그인 시점에 생성합니다.
                    - 로그인 이후 내 프로필은 GET /api/v1/users/me 에서 조회합니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공. accessToken 과 refreshToken 을 반환합니다.",
                    content = @Content(schema = @Schema(implementation = UserResponse.Login.class))
            )
    })
    CommonResponse<UserResponse.Login> nativeLogin(
            @PathVariable String provider,
            @RequestBody @Valid UserRequest.NativeLogin request
    );

    @Operation(
            summary = "카카오 OAuth redirect URI placeholder",
            description = """
                    카카오 개발자 콘솔의 redirect URI 검증과 브라우저 리다이렉트 도착 시 오류 화면 방지를 위한 placeholder 엔드포인트입니다.

                    주의:
                    - 이 엔드포인트는 실제 로그인을 처리하지 않습니다.
                    - authorization code 를 access/refresh token 으로 교환하지 않습니다.
                    - 클라이언트가 로그인하려면 POST /api/v1/auth/oauth/{provider}/web/login 으로 code 를 전달해야 합니다.
                    - 정상 도착 여부만 확인할 수 있도록 204 No Content 를 반환합니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "카카오 redirect URI 도착 확인용 응답. 본문 없음."
            )
    })
    ResponseEntity<Void> kakaoRedirect();
}
