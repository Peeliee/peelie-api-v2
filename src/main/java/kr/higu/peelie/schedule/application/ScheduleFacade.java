package kr.higu.peelie.schedule.application;

import kr.higu.peelie.schedule.domain.ScheduleCommand;
import kr.higu.peelie.schedule.domain.ScheduleInfo;
import kr.higu.peelie.schedule.domain.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleFacade {

    private final ScheduleService scheduleService;

    public List<ScheduleInfo.Main> getSchedules(String ownerPublicId) {
        return scheduleService.getSchedules(ownerPublicId);
    }

    public ScheduleInfo.Main getSchedule(String ownerPublicId, String schedulePublicId) {
        return scheduleService.getSchedule(ownerPublicId, schedulePublicId);
    }

    public ScheduleInfo.Main createSchedule(String ownerPublicId, ScheduleCommand.Create command) {
        return scheduleService.createSchedule(ownerPublicId, command);
    }
}
