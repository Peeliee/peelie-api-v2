package kr.higu.peelie.schedule.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import kr.higu.peelie.common.exception.InvalidParamException;
import kr.higu.peelie.common.jpa.BaseTimeEntity;
import kr.higu.peelie.common.util.PublicIdGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(
        name = "schedules",
        indexes = {
                @Index(name = "idx_schedule_owner_meet_date", columnList = "owner_id, meet_date"),
                @Index(name = "idx_schedule_friend_user", columnList = "friend_user_id")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseTimeEntity {

    private static final String PREFIX_SCHEDULE = "sch_";
    private static final int DESCRIPTION_MAX_LENGTH = 200;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String schedulePublicId;

    @Column(nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private Long friendUserId;

    @Column(nullable = false)
    private LocalDate meetDate;

    @Column(nullable = false, length = DESCRIPTION_MAX_LENGTH)
    private String description;

    private Schedule(Long ownerId, Long friendUserId, LocalDate meetDate, String description) {
        validateOwner(ownerId);
        validateFriendUser(ownerId, friendUserId);
        validateMeetDate(meetDate);
        validateDescription(description);

        this.schedulePublicId = PublicIdGenerator.randomCharacterWithPrefix(PREFIX_SCHEDULE);
        this.ownerId = ownerId;
        this.friendUserId = friendUserId;
        this.meetDate = meetDate;
        this.description = description.trim();
    }

    public static Schedule create(Long ownerId, Long friendUserId, LocalDate meetDate, String description) {
        return new Schedule(ownerId, friendUserId, meetDate, description);
    }

    private static void validateOwner(Long ownerId) {
        if (ownerId == null || ownerId <= 0) {
            throw new InvalidParamException("유저 Id가 유효하지 않습니다.");
        }
    }

    private static void validateFriendUser(Long ownerId, Long friendUserId) {
        if (friendUserId == null || friendUserId <= 0) {
            throw new InvalidParamException("친구의 Id가 유효하지 않습니다.");
        }
        if (ownerId.equals(friendUserId)) {
            throw new InvalidParamException("본인과의 일정은 만들 수 없습니다.");
        }
    }

    private static void validateMeetDate(LocalDate meetDate) {
        if (meetDate == null) {
            throw new InvalidParamException("만남 일자가 비어있습니다.");
        }
    }

    private static void validateDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new InvalidParamException("만남 자리 설명이 비어있습니다.");
        }
        if (description.trim().length() > DESCRIPTION_MAX_LENGTH) {
            throw new InvalidParamException("만남 자리 설명은 200자 이하여야 합니다.");
        }
    }
}
