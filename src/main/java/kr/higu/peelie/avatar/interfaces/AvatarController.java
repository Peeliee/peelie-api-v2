package kr.higu.peelie.avatar.interfaces;

import jakarta.validation.Valid;
import kr.higu.peelie.avatar.application.AvatarFacade;
import kr.higu.peelie.common.auth.UserContextHolder;
import kr.higu.peelie.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/avatar")
public class AvatarController {

    private final AvatarFacade avatarFacade;

    @PostMapping(value = "/messages/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter sendMessage(
            @RequestBody @Valid AvatarRequest.SendMessage request
    ) {
        String userPublicId = UserContextHolder.requireUserContext();
        return avatarFacade.sendMessage(userPublicId, request.toCommand());
    }

    @GetMapping("/rooms")
    public CommonResponse<AvatarResponse.ChatRoomList> getChatRooms() {
        String userPublicId = UserContextHolder.requireUserContext();
        var chatRooms = avatarFacade.getChatRooms(userPublicId).stream()
                .map(chatRoom -> new AvatarResponse.ChatRoomItem(
                        chatRoom.getChatRoomPublicId(),
                        chatRoom.getFriendId(),
                        chatRoom.getLastMessageAt()
                ))
                .toList();
        return CommonResponse.success(new AvatarResponse.ChatRoomList(chatRooms));
    }

    @GetMapping("/rooms/{chatRoomPublicId}/messages")
    public CommonResponse<AvatarResponse.ChatMessageList> getMessages(
            @PathVariable String chatRoomPublicId
    ) {
        String userPublicId = UserContextHolder.requireUserContext();
        var messages = avatarFacade.getMessages(userPublicId, chatRoomPublicId).stream()
                .map(message -> new AvatarResponse.ChatMessageItem(
                        message.getId(),
                        message.getRole().name(),
                        message.getContent(),
                        message.getCreatedAt()
                ))
                .toList();
        return CommonResponse.success(new AvatarResponse.ChatMessageList(messages));
    }
}
