package kr.higu.peelie.avatar.infra;

import kr.higu.peelie.avatar.domain.ChatRoom;
import kr.higu.peelie.avatar.domain.ChatRoomReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChatRoomReaderImpl implements ChatRoomReader {

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public Optional<ChatRoom> findByChatRoomPublicId(String chatRoomPublicId) {
        return chatRoomRepository.findByChatRoomPublicId(chatRoomPublicId);
    }

    @Override
    public Optional<ChatRoom> findByUserIdAndFriendId(Long userId, Long friendId) {
        return chatRoomRepository.findByUserIdAndFriendId(userId, friendId);
    }

    @Override
    public List<ChatRoom> getChatRooms(Long userId) {
        return chatRoomRepository.findAllByUserIdOrderByLastMessageAtDesc(userId);
    }
}
