package kr.higu.peelie.schedule.domain;

import kr.higu.peelie.user.domain.PersonalityType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ScheduleInfo {

    private ScheduleInfo() {
    }

    @Getter
    @AllArgsConstructor
    public static class Main {
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

}
