package kr.higu.peelie.friendship.infra;

import kr.higu.peelie.common.util.PublicIdGenerator;
import kr.higu.peelie.friendship.domain.FriendCode;
import kr.higu.peelie.friendship.domain.FriendCodeManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FriendCodeManagerImpl implements FriendCodeManager {

    private static final String USER_TO_CODE_KEY_FORMAT = "friend:user:%s"; // userId -> code
    private static final String CODE_TO_USER_KEY_FORMAT = "friend:code:%s"; // code -> userId
    private static final int CODE_LENGTH = 12;
    private static final int TTL_SECONDS = 300;

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public FriendCode getOrCreate(Long userId) {
        String userKey = USER_TO_CODE_KEY_FORMAT.formatted(userId);
        String userCode = stringRedisTemplate.opsForValue().get(userKey);
        if (userCode == null || userCode.isBlank()) return createCode(userId);

        String codeKey = CODE_TO_USER_KEY_FORMAT.formatted(userCode);
        String mappedUserId = stringRedisTemplate.opsForValue().get(codeKey);
        if (!String.valueOf(userId).equals(mappedUserId)) return createCode(userId);

        return new FriendCode(userId, userCode);
    }

    @Override
    public Optional<Long> findUserIdByCode(String code) {
        String trimmedCode = code == null ? "" : code.trim();
        String mappedUserId = stringRedisTemplate.opsForValue().get(CODE_TO_USER_KEY_FORMAT.formatted(trimmedCode));
        if (mappedUserId == null || mappedUserId.isBlank()) return Optional.empty();

        Long userId = Long.valueOf(mappedUserId);
        String currentCode = stringRedisTemplate.opsForValue().get(USER_TO_CODE_KEY_FORMAT.formatted(userId));

        if (!trimmedCode.equals(currentCode)) return Optional.empty();

        return Optional.of(userId);
    }

    private FriendCode createCode(Long userId) {
        Duration ttl = Duration.ofSeconds(TTL_SECONDS);
        String code = generateUniqueCode(userId, ttl);
        String userKey = USER_TO_CODE_KEY_FORMAT.formatted(userId);

        try {
            stringRedisTemplate.opsForValue().set(userKey, code, ttl);
        } catch (RuntimeException e) {
            stringRedisTemplate.delete(CODE_TO_USER_KEY_FORMAT.formatted(code));
            throw e;
        }

        return new FriendCode(userId, code);
    }

    private String generateUniqueCode(Long userId, Duration ttl) {
        while (true) {
            String code = PublicIdGenerator.randomNumeric(CODE_LENGTH);
            Boolean claimed = stringRedisTemplate.opsForValue()
                    .setIfAbsent(CODE_TO_USER_KEY_FORMAT.formatted(code), String.valueOf(userId), ttl);

            if (Boolean.TRUE.equals(claimed)) {
                return code;
            }
        }
    }
}
