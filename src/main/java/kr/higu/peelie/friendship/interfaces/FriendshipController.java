package kr.higu.peelie.friendship.interfaces;

import jakarta.validation.Valid;
import kr.higu.peelie.common.auth.UserContextHolder;
import kr.higu.peelie.common.response.CommonResponse;
import kr.higu.peelie.friendship.application.FriendshipFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
public class FriendshipController implements FriendshipControllerDoc {

    private final FriendshipFacade friendshipFacade;
    private final FriendshipMapper friendshipMapper;

    @GetMapping("/code")
    public CommonResponse<FriendResponse.InviteCode> getInviteCode() {
        String userPublicId = UserContextHolder.requireUserContext();
        var friendCode = friendshipFacade.getInviteCode(userPublicId);
        return CommonResponse.success(friendshipMapper.toInviteCode(friendCode));
    }

    @PostMapping
    public CommonResponse<FriendResponse.AddFriend> addFriend(
            @RequestBody @Valid FriendshipRequest.Add request
    ) {
        String userPublicId = UserContextHolder.requireUserContext();
        var friendInfo = friendshipFacade.addFriend(userPublicId, request.getCode());
        return CommonResponse.success(friendshipMapper.toAddFriend(friendInfo));
    }

    @GetMapping
    public CommonResponse<FriendResponse.FriendList> getFriends() {
        String userPublicId = UserContextHolder.requireUserContext();
        var friends = friendshipFacade.getFriends(userPublicId);
        return CommonResponse.success(friendshipMapper.toFriendList(friends));
    }
}
