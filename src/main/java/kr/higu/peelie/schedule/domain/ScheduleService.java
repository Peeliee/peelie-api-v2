package kr.higu.peelie.schedule.domain;

import java.util.List;

public interface ScheduleService {
    List<ScheduleInfo.Main> getSchedules(String ownerPublicId);
    ScheduleInfo.Main getSchedule(String ownerPublicId, String schedulePublicId);
    ScheduleInfo.Main createSchedule(String ownerPublicId, ScheduleCommand.Create command);
}
