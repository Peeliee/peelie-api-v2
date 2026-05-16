package kr.higu.peelie.schedule.infra;

import kr.higu.peelie.schedule.domain.Schedule;
import kr.higu.peelie.schedule.domain.ScheduleStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleStoreImpl implements ScheduleStore {

    private final ScheduleRepository scheduleRepository;

    @Override
    public Schedule store(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }
}
