package kr.higu.peelie.onboarding.domain;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.extension.ExtendWith;
import kr.higu.peelie.common.config.JpaAuditingConfig;
import kr.higu.peelie.onboarding.infra.QuestionRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.jpa.show-sql=true",
        "spring.jpa.properties.hibernate.format_sql=true",
        "logging.level.org.hibernate.SQL=DEBUG",
        "logging.level.org.hibernate.orm.jdbc.bind=TRACE"
})
@Import(JpaAuditingConfig.class)
@ActiveProfiles("test")
@ExtendWith(OutputCaptureExtension.class)
class OnboardingPersistenceSqlLogTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("cascade 저장도 선택지 insert SQL은 개별로 실행된다")
    void cascadePersistLogsOneInsertPerAnswerOption(CapturedOutput output) {
        // given
        Question question = Question.builder()
                .content("질문")
                .purpose("목적")
                .displayOrder(1)
                .questionType(Question.QuestionType.CHOICE)
                .build();
        question.addAnswerOption("선택지 1", 1, "tag1");
        question.addAnswerOption("선택지 2", 2, "tag2");

        // when
        questionRepository.saveAndFlush(question);

        // then
        String logs = output.getOut() + output.getErr();
        assertThat(countOccurrences(logs, "insert into onboarding_questions")).isGreaterThanOrEqualTo(1);
        assertThat(countOccurrences(logs, "insert into answer_options")).isGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("saveAll은 배치가 아니라 요소별 insert SQL이 실행된다")
    void saveAllStillLogsSeparateInsertStatements(CapturedOutput output) {
        // given
        Question question = Question.builder()
                .content("질문")
                .purpose("목적")
                .displayOrder(1)
                .questionType(Question.QuestionType.CHOICE)
                .build();
        questionRepository.saveAndFlush(question);

        AnswerOptionJpaTestRepository answerOptionRepository =
                new JpaRepositoryFactory(entityManager).getRepository(AnswerOptionJpaTestRepository.class);

        AnswerOption option1 = new AnswerOption("선택지 1", 1, "tag1");
        option1.setQuestion(question);

        AnswerOption option2 = new AnswerOption("선택지 2", 2, "tag2");
        option2.setQuestion(question);

        // when
        answerOptionRepository.saveAll(List.of(option1, option2));
        answerOptionRepository.flush();

        // then
        String logs = output.getOut() + output.getErr();
        assertThat(countOccurrences(logs, "insert into answer_options")).isGreaterThanOrEqualTo(2);
    }

    private int countOccurrences(String source, String target) {
        String normalizedSource = source.replaceAll("\\s+", " ").toLowerCase();
        String normalizedTarget = target.replaceAll("\\s+", " ").toLowerCase();
        return normalizedSource.split(normalizedTarget, -1).length - 1;
    }

    private interface AnswerOptionJpaTestRepository extends JpaRepository<AnswerOption, Long> {
    }
}
