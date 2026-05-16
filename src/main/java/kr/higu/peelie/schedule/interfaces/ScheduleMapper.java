package kr.higu.peelie.schedule.interfaces;

import kr.higu.peelie.schedule.domain.ScheduleInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduleMapper {

    public ScheduleResponse.Schedule toSchedule(ScheduleInfo.Main scheduleInfo) {
        return new ScheduleResponse.Schedule(
                scheduleInfo.getId(),
                scheduleInfo.getMeetDate(),
                scheduleInfo.getDescription(),
                scheduleInfo.getCreatedAt(),
                new ScheduleResponse.Friend(
                        scheduleInfo.getFriendUser().getId(),
                        scheduleInfo.getFriendUser().getName(),
                        scheduleInfo.getFriendUser().getPersonalityType()
                )
        );
    }

    public ScheduleResponse.ScheduleList toScheduleList(List<ScheduleInfo.Main> scheduleInfos) {
        return new ScheduleResponse.ScheduleList(
                scheduleInfos.stream()
                        .map(this::toSchedule)
                        .toList()
        );
    }
}
