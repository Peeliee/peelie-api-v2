package kr.higu.peelie.avatar.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SendMessageCommand {
    private final String friendPublicId;
    private final String message;
}
