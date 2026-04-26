package kr.higu.peelie.integration.onboarding;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.higu.peelie.onboarding.domain.event.OnboardingCompleteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OnboardingEventHandler {
    private static final String EVENT_TYPE = "onboarding.completed";

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    @Value("${integration.redis.stream.onboarding-completed-key:stream:onboarding-events}")
    private String onboardingCompletedStreamKey;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OnboardingCompleteEvent event) {
        try {
            String answersJson = createAnswersJson(event);

            Map<String, String> payload = new LinkedHashMap<>();
            payload.put("event_type", EVENT_TYPE);
            payload.put("event_id", event.getEventId());
            payload.put("user_id", event.getUserId().toString());
            payload.put("nickname", event.getNickname());
            payload.put("answers_json", answersJson);
            payload.put("occurred_at", event.getOccurredAt().toString());

            stringRedisTemplate.opsForStream()
                    .add(StreamRecords.string(payload).withStreamKey(onboardingCompletedStreamKey));
        } catch (Exception e) {
            log.error("failed to publish onboarding event. eventId={}", event.getEventId(), e);
        }
    }

    private String createAnswersJson(OnboardingCompleteEvent event) throws JsonProcessingException {
        List<Map<String, Object>> answers = event.getAnswers().stream()
                .map(answer -> {
                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("question_id", answer.getQuestionId());
                    item.put("purpose", answer.getPurpose());
                    item.put("answer", answer.getAnswer());
                    item.put("option_tag", answer.getOptionTag());
                    return item;
                })
                .toList();

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("answers", answers);
        return objectMapper.writeValueAsString(payload);
    }
}
