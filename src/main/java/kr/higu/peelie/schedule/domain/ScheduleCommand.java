package kr.higu.peelie.schedule.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

public class ScheduleCommand {

    private ScheduleCommand() {
    }

    @Getter
    @RequiredArgsConstructor
    public static class Create {
        private final String friendUserId;
        private final LocalDate meetDate;
        private final String description;
    }
}
