package kr.higu.peelie.schedule.domain;

import java.util.List;

public interface ScheduleReader {
    List<Schedule> getSchedules(Long ownerId);
    Schedule getSchedule(String schedulePublicId, Long ownerId);
}
