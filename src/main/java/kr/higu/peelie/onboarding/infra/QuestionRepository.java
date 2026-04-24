package kr.higu.peelie.onboarding.infra;

import kr.higu.peelie.onboarding.domain.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByDisplayOrder(int displayOrder);
    List<Question> findAllByOrderByDisplayOrderAsc();
}
