package kr.higu.peelie.avatar.infra;

import kr.higu.peelie.avatar.domain.ChatMessage;
import kr.higu.peelie.avatar.domain.ChatMessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatMessageStoreImpl implements ChatMessageStore {

    private final ChatMessageRepository chatMessageRepository;

    @Override
    public void storeAll(List<ChatMessage> chatMessages) {
        chatMessageRepository.saveAll(chatMessages);
    }

    @Override
    public void deleteByChatRoomId(Long chatRoomId) {
        chatMessageRepository.deleteByChatRoomId(chatRoomId);
    }
}
