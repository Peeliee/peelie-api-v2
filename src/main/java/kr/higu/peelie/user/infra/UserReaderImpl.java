package kr.higu.peelie.user.infra;

import kr.higu.peelie.common.exception.EntityNotFoundException;
import kr.higu.peelie.user.domain.User;
import kr.higu.peelie.user.domain.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {

    private final UserRepository userRepository;

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자가 존재하지않습니다.. userId=" + userId));
    }

    @Override
    public User getUser(String userPublicId) {
        return userRepository.findByUserPublicId(userPublicId)
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자가 존재하지않습니다.. userPublicId=" + userPublicId));
    }

    @Override
    public User findUser(User.Provider provider, String oid) {
        return userRepository.findByProviderAndOid(provider, oid).orElse(null);
    }
}
