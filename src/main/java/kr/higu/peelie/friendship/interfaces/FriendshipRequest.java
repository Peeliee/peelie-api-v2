package kr.higu.peelie.friendship.interfaces;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class FriendshipRequest {

    @Getter
    @NoArgsConstructor
    public static class Add {

        @NotBlank
        @Pattern(regexp = "^\\d{12}$")
        private String code;
    }
}
