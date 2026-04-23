package kr.higu.peelie.onboarding.infra;

import kr.higu.peelie.onboarding.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByOrderByDisplayOrderAsc();
}
