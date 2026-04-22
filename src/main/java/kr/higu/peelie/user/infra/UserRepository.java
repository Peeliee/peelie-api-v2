package kr.higu.peelie.user.infra;

import kr.higu.peelie.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserPublicId(String publicId);
    Optional<User> findByProviderAndOid(User.Provider provider, String oid);
}
