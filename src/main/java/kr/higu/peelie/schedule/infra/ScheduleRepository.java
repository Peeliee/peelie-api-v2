package kr.higu.peelie.schedule.infra;

import kr.higu.peelie.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByOwnerIdOrderByMeetDateDesc(Long ownerId);
    Optional<Schedule> findBySchedulePublicIdAndOwnerId(String schedulePublicId, Long ownerId);
}
