package kr.higu.peelie.user.interfaces;

import jakarta.validation.Valid;
import kr.higu.peelie.common.auth.UserContextHolder;
import kr.higu.peelie.common.response.CommonResponse;
import kr.higu.peelie.user.application.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController implements UserControllerDoc{

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

    @PatchMapping("/me/nickname")
    public CommonResponse<String> changeNickname(
            @RequestBody @Valid UserRequest.Nickname request) {
        String userToken = UserContextHolder.requireUserContext();
        userFacade.changeNickname(userToken, request.getNewNickname());
        return CommonResponse.success("닉네임이 변경되었습니다.");
    }

    @GetMapping("/oauth2/code/kakao")
    public ResponseEntity<Void> kakaoRedirect() {
        return ResponseEntity.noContent().build();
    }
}
