package kr.higu.peelie.user.interfaces;

import jakarta.validation.Valid;
import kr.higu.peelie.common.response.CommonResponse;
import kr.higu.peelie.user.application.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthControllerDoc {

    private final UserFacade userFacade;

    @PostMapping("/oauth/{provider}/web/login")
    public CommonResponse<UserResponse.Login> webLogin(
            @PathVariable String provider,
            @RequestBody @Valid UserRequest.WebLogin request
    ) {
        var result = userFacade.webLogin(provider, request.getCode());
        return CommonResponse.success(new UserResponse.Login(result));
    }

    @PostMapping("/oauth/{provider}/native/login")
    public CommonResponse<UserResponse.Login> nativeLogin(
            @PathVariable String provider,
            @RequestBody @Valid UserRequest.NativeLogin request
    ) {
        var result = userFacade.nativeLogin(provider, request.getAccessToken());
        return CommonResponse.success(new UserResponse.Login(result));
    }

    @GetMapping("/oauth2/code/kakao")
    public ResponseEntity<Void> kakaoRedirect() {
        return ResponseEntity.noContent().build();
    }
}
