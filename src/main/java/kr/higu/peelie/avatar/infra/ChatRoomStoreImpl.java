package kr.higu.peelie.avatar.infra;

import kr.higu.peelie.avatar.domain.ChatRoom;
import kr.higu.peelie.avatar.domain.ChatRoomStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatRoomStoreImpl implements ChatRoomStore {

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public ChatRoom store(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public void delete(ChatRoom chatRoom) {
        chatRoomRepository.delete(chatRoom);
    }
}
