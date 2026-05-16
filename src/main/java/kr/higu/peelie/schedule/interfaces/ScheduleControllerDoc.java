package kr.higu.peelie.schedule.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.higu.peelie.common.response.CommonResponse;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(
        name = "3. Schedule",
        description = "일정 API. 정책은 NestJS Schedule 을 따르되, ChatRoom 자동 생성은 다음 단계에서 별도로 연결합니다."
)
public interface ScheduleControllerDoc {

    @Operation(
            summary = "내 일정 목록",
            description = """
                    로그인한 사용자가 생성한 일정 목록을 조회합니다.

                    정책:
                    - 과거/미래 일정을 모두 반환합니다.
                    - meetDate 내림차순으로 정렬합니다.
                    - friendUser 는 일정 대상 친구 정보입니다.
                    - 현재 초안에서는 ChatRoom 은 아직 생성/응답하지 않습니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "일정 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = ScheduleResponse.ScheduleList.class))
            )
    })
    CommonResponse<ScheduleResponse.ScheduleList> getSchedules();

    @Operation(
            summary = "일정 단건 조회",
            description = """
                    schedule id 로 내 일정 단건을 조회합니다.

                    - 본인 소유 일정만 조회할 수 있습니다.
                    - 없는 일정이거나 내 일정이 아니면 실패 응답을 반환합니다.
                    - 현재 초안에서는 ChatRoom 은 아직 생성/응답하지 않습니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "일정 단건 조회 성공",
                    content = @Content(schema = @Schema(implementation = ScheduleResponse.Schedule.class))
            )
    })
    CommonResponse<ScheduleResponse.Schedule> getSchedule(@PathVariable String scheduleId);

    @Operation(
            summary = "일정 등록",
            description = """
                    친구와의 일정을 등록합니다.

                    요청 정책:
                    - friendUserId 는 친구 사용자의 userPublicId 입니다.
                    - friendUserId 대상은 내가 직접 추가한 친구여야 합니다.
                    - 본인과의 일정은 만들 수 없습니다.
                    - meetDate 는 YYYY-MM-DD 형식의 날짜입니다.
                    - description 은 1~200자입니다.

                    NestJS 원본 정책에는 Schedule 등록 시 ChatRoom 자동 생성이 포함되어 있습니다.
                    이 Spring 초안에서는 Schedule 서브도메인까지만 구현하고, ChatRoom 연결은 다음 작업에서 붙입니다.
                    """,
            requestBody = @RequestBody(
                    required = true,
                    description = "일정 등록 요청",
                    content = @Content(schema = @Schema(implementation = ScheduleRequest.Create.class))
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "일정 등록 성공",
                    content = @Content(schema = @Schema(implementation = ScheduleResponse.Schedule.class))
            )
    })
    CommonResponse<ScheduleResponse.Schedule> createSchedule(@RequestBody @Valid ScheduleRequest.Create request);
}
