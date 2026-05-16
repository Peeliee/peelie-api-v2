package kr.higu.peelie.schedule.interfaces;

import kr.higu.peelie.user.domain.PersonalityType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ScheduleResponse {

    private ScheduleResponse() {
    }

    @Getter
    @AllArgsConstructor
    public static class Schedule {
        private final String id;
        private final LocalDate meetDate;
        private final String description;
        private final LocalDateTime createdAt;
        private final Friend friendUser;
    }

    @Getter
    @AllArgsConstructor
    public static class Friend {
        private final String id;
        private final String name;
        private final PersonalityType personalityType;
    }

    @Getter
    @AllArgsConstructor
    public static class ScheduleList {
        private final List<Schedule> schedules;
    }
}
