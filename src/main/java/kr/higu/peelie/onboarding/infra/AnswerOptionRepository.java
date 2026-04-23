package kr.higu.peelie.onboarding.infra;

import kr.higu.peelie.onboarding.domain.AnswerOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Long> {
}
