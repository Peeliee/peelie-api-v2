package kr.higu.peelie.avatar.interfaces;

import jakarta.validation.constraints.NotBlank;
import kr.higu.peelie.avatar.domain.SendMessageCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AvatarRequest {

    private AvatarRequest() {
    }

    @Getter
    @NoArgsConstructor
    public static class SendMessage {
        @NotBlank
        private String friendPublicId;

        @NotBlank
        private String message;

        public SendMessageCommand toCommand() {
            return new SendMessageCommand(friendPublicId, message);
        }
    }
}
