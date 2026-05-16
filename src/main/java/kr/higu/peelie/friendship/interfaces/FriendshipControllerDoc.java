package kr.higu.peelie.friendship.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.higu.peelie.common.response.CommonResponse;

@Tag(name = "3. Friend", description = "친구 관리 API")
public interface FriendshipControllerDoc {
    @Operation(
            summary = "내 초대 코드 조회",
            description = "로그인한 사용자의 영구 friendCode 를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "친구 초대 코드 조회 성공",
                    content = @Content(schema = @Schema(implementation = FriendResponse.InviteCode.class))
            )
    })
    CommonResponse<FriendResponse.InviteCode> getInviteCode();

    @Operation(
            summary = "친구 추가",
            description = "friendCode 를 입력해 owner -> friendUser 단방향 친구 관계를 생성합니다.",
            requestBody = @RequestBody(
                    required = true,
                    description = "친구 추가 요청",
                    content = @Content(schema = @Schema(implementation = FriendshipRequest.Add.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "친구 추가 성공",
                    content = @Content(schema = @Schema(implementation = FriendResponse.AddFriend.class))
            )
    })
    CommonResponse<FriendResponse.AddFriend> addFriend(FriendshipRequest.Add request);

    @Operation(summary = "친구 목록 조회", description = "로그인한 사용자가 직접 추가한 친구 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "친구 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = FriendResponse.FriendList.class))
            )
    })
    CommonResponse<FriendResponse.FriendList> getFriends();
}
