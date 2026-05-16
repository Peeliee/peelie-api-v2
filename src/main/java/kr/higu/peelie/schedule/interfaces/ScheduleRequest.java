package kr.higu.peelie.schedule.interfaces;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.higu.peelie.schedule.domain.ScheduleCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class ScheduleRequest {

    private ScheduleRequest() {
    }

    @Getter
    @NoArgsConstructor
    public static class Create {
        @NotBlank
        private String friendUserId;

        @NotNull
        private LocalDate meetDate;

        @NotBlank
        @Size(min = 1, max = 200)
        private String description;

        public ScheduleCommand.Create toCommand() {
            return new ScheduleCommand.Create(friendUserId, meetDate, description);
        }
    }
}
