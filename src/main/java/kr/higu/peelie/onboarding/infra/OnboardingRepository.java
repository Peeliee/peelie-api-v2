package kr.higu.peelie.onboarding.infra;

import kr.higu.peelie.onboarding.domain.Onboarding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnboardingRepository extends JpaRepository<Onboarding, Long> {
}
