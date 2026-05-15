package kr.higu.peelie.user.interfaces;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequest {

    @Getter
    @NoArgsConstructor
    public static class NativeLogin {
        @NotBlank(message = "accessToken is required")
        private String accessToken;
    }

    @Getter
    @NoArgsConstructor
    public static class WebLogin {
        @NotBlank(message = "code is required")
        private String code;
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateUser {
        @NotBlank
        private String name;
        @NotBlank
        private String personalityType;
    }

    @Getter
    @NoArgsConstructor
    public static class Onboarding {
        @NotBlank
        private String name;
        @NotBlank
        private String personalityType;
    }
}
