package kr.higu.peelie.user.interfaces;

import jakarta.validation.Valid;
import kr.higu.peelie.common.auth.UserContextHolder;
import kr.higu.peelie.common.response.CommonResponse;
import kr.higu.peelie.user.application.UserFacade;
import kr.higu.peelie.user.domain.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController implements UserControllerDoc{

    private final UserFacade userFacade;

    @GetMapping("/me")
    public CommonResponse<UserResponse.User> getMe() {
        String userPublicId = UserContextHolder.getUserContext();
        UserInfo userInfo = userFacade.getUser(userPublicId);
        UserResponse.User response = new UserResponse.User(userInfo);
        return CommonResponse.success(response);
    }

    @PatchMapping("/me")
    public CommonResponse<UserResponse.User> updateUser(@RequestBody @Valid UserRequest.UpdateUser request) {
        String userPublicId = UserContextHolder.getUserContext();
        UserInfo userInfo = userFacade.updateUser(userPublicId, request.getName(), request.getPersonalityType());
        UserResponse.User response = new UserResponse.User(userInfo);
        return CommonResponse.success(response);
    }

    @PostMapping("/onboarding")
    public CommonResponse<UserResponse.User> completeOnboarding(@RequestBody UserRequest.Onboarding request) {
        String userPublicId = UserContextHolder.getUserContext();
        UserInfo userInfo = userFacade.completeOnboarding(userPublicId, request.getName(), request.getPersonalityType());
        UserResponse.User response = new UserResponse.User(userInfo);
        return CommonResponse.success(response);
    }
}
