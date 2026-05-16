package kr.higu.peelie.schedule.infra;

import kr.higu.peelie.common.exception.EntityNotFoundException;
import kr.higu.peelie.schedule.domain.Schedule;
import kr.higu.peelie.schedule.domain.ScheduleReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduleReaderImpl implements ScheduleReader {

    private final ScheduleRepository scheduleRepository;

    @Override
    public List<Schedule> getSchedules(Long ownerId) {
        return scheduleRepository.findAllByOwnerIdOrderByMeetDateDesc(ownerId);
    }

    @Override
    public Schedule getSchedule(String schedulePublicId, Long ownerId) {
        return scheduleRepository.findBySchedulePublicIdAndOwnerId(schedulePublicId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException("일정을 찾을 수 없습니다. schedulePublicId=" + schedulePublicId));
    }
}
