package kr.higu.peelie.schedule.interfaces;

import jakarta.validation.Valid;
import kr.higu.peelie.common.auth.UserContextHolder;
import kr.higu.peelie.common.response.CommonResponse;
import kr.higu.peelie.schedule.application.ScheduleFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController implements ScheduleControllerDoc {

    private final ScheduleFacade scheduleFacade;
    private final ScheduleMapper scheduleMapper;

    @GetMapping
    public CommonResponse<ScheduleResponse.ScheduleList> getSchedules() {
        String ownerPublicId = UserContextHolder.requireUserContext();
        var schedules = scheduleFacade.getSchedules(ownerPublicId);
        return CommonResponse.success(scheduleMapper.toScheduleList(schedules));
    }

    @GetMapping("/{scheduleId}")
    public CommonResponse<ScheduleResponse.Schedule> getSchedule(@PathVariable String scheduleId) {
        String ownerPublicId = UserContextHolder.requireUserContext();
        var schedule = scheduleFacade.getSchedule(ownerPublicId, scheduleId);
        return CommonResponse.success(scheduleMapper.toSchedule(schedule));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<ScheduleResponse.Schedule> createSchedule(
            @RequestBody @Valid ScheduleRequest.Create request
    ) {
        String ownerPublicId = UserContextHolder.requireUserContext();
        var schedule = scheduleFacade.createSchedule(ownerPublicId, request.toCommand());
        return CommonResponse.success(scheduleMapper.toSchedule(schedule));
    }
}
