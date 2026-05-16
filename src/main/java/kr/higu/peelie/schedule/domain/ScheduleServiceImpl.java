package kr.higu.peelie.schedule.domain;

import kr.higu.peelie.common.exception.InvalidParamException;
import kr.higu.peelie.friendship.domain.FriendshipReader;
import kr.higu.peelie.user.domain.User;
import kr.higu.peelie.user.domain.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final UserReader userReader;
    private final FriendshipReader friendshipReader;
    private final ScheduleReader scheduleReader;
    private final ScheduleStore scheduleStore;

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleInfo.Main> getSchedules(String ownerPublicId) {
        User owner = userReader.getUser(ownerPublicId);
        return scheduleReader.getSchedules(owner.getId()).stream()
                .map(schedule -> toInfo(schedule, userReader.getUser(schedule.getFriendUserId())))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleInfo.Main getSchedule(String ownerPublicId, String schedulePublicId) {
        User owner = userReader.getUser(ownerPublicId);
        Schedule schedule = scheduleReader.getSchedule(schedulePublicId, owner.getId());
        User friend = userReader.getUser(schedule.getFriendUserId());
        return toInfo(schedule, friend);
    }

    @Override
    @Transactional
    public ScheduleInfo.Main createSchedule(String ownerPublicId, ScheduleCommand.Create command) {
        User owner = userReader.getUser(ownerPublicId);
        User friend = userReader.getUser(command.getFriendUserId());

        friendshipReader.findFriendship(owner.getId(), friend.getId())
                .orElseThrow(() -> new InvalidParamException("친구 목록에 없는 유저입니다."));

        Schedule schedule = Schedule.create(
                owner.getId(),
                friend.getId(),
                command.getMeetDate(),
                command.getDescription()
        );
        Schedule storedSchedule = scheduleStore.store(schedule);

        return toInfo(storedSchedule, friend);
    }

    private ScheduleInfo.Main toInfo(Schedule schedule, User friend) {
        return new ScheduleInfo.Main(
                schedule.getSchedulePublicId(),
                schedule.getMeetDate(),
                schedule.getDescription(),
                schedule.getCreatedAt(),
                new ScheduleInfo.Friend(
                        friend.getUserPublicId(),
                        friend.getName(),
                        friend.getPersonalityType()
                )
        );
    }
}
