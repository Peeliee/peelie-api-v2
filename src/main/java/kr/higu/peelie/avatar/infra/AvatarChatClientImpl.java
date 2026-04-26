package kr.higu.peelie.avatar.infra;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.higu.peelie.avatar.domain.AvatarChatClient;
import kr.higu.peelie.avatar.domain.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class AvatarChatClientImpl implements AvatarChatClient {

    @Qualifier("avatarFastApiRestClient")
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    @Value("${integration.avatar.fastapi.stream-path:/v1/chat/stream}")
    private String streamPath;

    @Override
    public String streamChat(
            Long friendId,
            String message,
            List<ChatMessage> history,
            Consumer<String> deltaListener
    ) {
        Request payload = new Request(
                friendId,
                message,
                history.stream()
                        .map(chatMessage -> new History(
                                chatMessage.getRole().name(),
                                chatMessage.getContent()
                        ))
                        .toList()
        );

        return restClient.post()
                .uri(streamPath)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .body(payload)
                .exchange((request, response) -> readStream(response, deltaListener));
    }

    private String readStream(ClientHttpResponse response, Consumer<String> deltaListener) throws IOException {
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException("fast api chat stream request failed. status=" + response.getStatusCode().value());
        }

        StringBuilder answerBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))) {
            String line;
            String eventName = null;
            List<String> dataLines = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    if (eventName != null) {
                        String data = String.join("\n", dataLines);

                        if ("meta".equals(eventName)) {
                        } else if ("delta".equals(eventName)) {
                            DeltaEvent deltaEvent = objectMapper.readValue(data, DeltaEvent.class);
                            answerBuilder.append(deltaEvent.getContent());
                            deltaListener.accept(deltaEvent.getContent());
                        } else if ("done".equals(eventName)) {
                            DoneEvent doneEvent = objectMapper.readValue(data, DoneEvent.class);
                            return doneEvent.getAnswer();
                        } else if ("error".equals(eventName)) {
                            ErrorEvent errorEvent = objectMapper.readValue(data, ErrorEvent.class);
                            throw new IllegalStateException(errorEvent.getMessage());
                        }
                    }

                    eventName = null;
                    dataLines = new ArrayList<>();
                    continue;
                }

                if (line.startsWith("event:")) {
                    eventName = line.substring("event:".length()).trim();
                    continue;
                }

                if (line.startsWith("data:")) {
                    dataLines.add(line.substring("data:".length()).trim());
                }
            }
        }

        return answerBuilder.toString();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Request {
        @JsonProperty("user_id")
        private Long friendId;
        private String message;
        private List<History> history;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class History {
        private String role;
        private String content;
    }

    @Getter
    @NoArgsConstructor
    private static class DeltaEvent {
        private String content;
    }

    @Getter
    @NoArgsConstructor
    private static class DoneEvent {
        private String answer;
    }

    @Getter
    @NoArgsConstructor
    private static class ErrorEvent {
        private String message;
    }
}
