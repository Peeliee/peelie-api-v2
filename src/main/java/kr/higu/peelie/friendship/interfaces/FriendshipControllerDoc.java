package kr.higu.peelie.friendship.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.higu.peelie.common.response.CommonResponse;

@Tag(
        name = "2. Friendship",
        description = "친구 관리 API. 친구 관계는 owner -> friendUser 단방향이며, friendCode 는 User 가 영구적으로 보유하는 8자리 코드입니다."
)
public interface FriendshipControllerDoc {
    @Operation(
            summary = "내 friendCode 조회",
            description = """
                    로그인한 사용자의 영구 friendCode 를 조회합니다.

                    friendCode 정책:
                    - 유저 생성 시 발급되는 8자리 lowercase 알파벳/숫자 코드입니다.
                    - Redis 임시 초대 코드가 아니며 만료되지 않습니다.
                    - 다른 사용자가 이 값을 친구 추가 요청의 friendCode 로 전달합니다.
                    - /api/v1/users/me 응답에도 동일한 friendCode 가 포함됩니다.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "friendCode 조회 성공",
                    content = @Content(schema = @Schema(implementation = FriendResponse.InviteCode.class))
            )
    })
    CommonResponse<FriendResponse.InviteCode> getInviteCode();

    @Operation(
            summary = "친구 추가",
            description = """
                    friendCode 를 입력해 친구 관계를 생성합니다.

                    친구 관계 정책:
                    - 관계는 owner -> friendUser 단방향입니다.
                    - A가 B를 추가해도 B의 친구 목록에 A가 자동으로 생기지 않습니다.
                    - B도 A를 보려면 B가 A의 friendCode 로 별도 추가해야 합니다.
                    - 이미 같은 방향(owner -> friendUser)으로 추가된 친구는 중복 추가할 수 없습니다.
                    - 본인의 friendCode 로 자기 자신을 친구 추가할 수 없습니다.
                    - 일정 생성 시에는 내가 직접 추가한 친구(owner -> friendUser 관계가 있는 사용자)만 대상이 됩니다.

                    요청 정책:
                    - friendCode 는 8자리 lowercase 알파벳/숫자 형식입니다.
                    """,
            requestBody = @RequestBody(
                    required = true,
                    description = "추가할 사용자의 영구 friendCode 를 전달합니다.",
                    content = @Content(schema = @Schema(implementation = FriendshipRequest.Add.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "친구 추가 성공. 추가된 친구의 public id, 이름, personalityType 을 반환합니다.",
                    content = @Content(schema = @Schema(implementation = FriendResponse.AddFriend.class))
            )
    })
    CommonResponse<FriendResponse.AddFriend> addFriend(FriendshipRequest.Add request);

    @Operation(
            summary = "친구 목록 조회",
            description = """
                    로그인한 사용자가 직접 추가한 친구 목록을 조회합니다.

                    목록 정책:
                    - owner 가 현재 사용자와 일치하는 친구 관계만 반환합니다.
                    - 나를 친구로 추가한 사용자는 내 목록에 자동으로 포함되지 않습니다.
                    - 응답의 friendPublicId 는 친구 사용자를 식별하는 공개 id 입니다.
                    - personalityType 은 일정/채팅 화면에서 친구 성향 표시와 LLM 컨텍스트에 사용할 수 있습니다.
                    - 목록은 최근 추가된 친구부터 반환합니다.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "친구 목록 조회 성공. 내가 직접 추가한 친구만 반환합니다.",
                    content = @Content(schema = @Schema(implementation = FriendResponse.FriendList.class))
            )
    })
    CommonResponse<FriendResponse.FriendList> getFriends();
}
