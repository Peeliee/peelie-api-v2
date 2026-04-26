package kr.higu.peelie.avatar.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatStreamMeta {
    private final String chatRoomPublicId;
    private final String userPublicId;
    private final String friendPublicId;
}
