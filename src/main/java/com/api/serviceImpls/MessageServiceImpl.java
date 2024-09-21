package com.api.serviceImpls;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.api.dtos.MessageDto;
import com.api.entities.Chat;
import com.api.entities.Message;
import com.api.entities.User;
import com.api.exceptions.ResourceNotFoundExceptionById;
import com.api.exceptions.ResourceNotFoundExceptionByUsername;
import com.api.repositories.ChatRepository;
import com.api.repositories.MessageRepository;
import com.api.repositories.UserRepository;
import com.api.services.MessageService;


@Service
public class MessageServiceImpl implements MessageService {

	private ChatRepository chatRepository;

	private UserRepository userRepository;

	private MessageRepository messageRepository;

	private ModelMapper modelMapper;

	public MessageServiceImpl(ChatRepository chatRepository, UserRepository userRepository,
			MessageRepository messageRepository, ModelMapper modelMapper) {
		this.chatRepository = chatRepository;
		this.userRepository = userRepository;
		this.messageRepository = messageRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public MessageDto createMessage(MessageDto messageDto, String username, Long chatId) {
		Chat chat = this.chatRepository.findById(chatId)
				.orElseThrow(() -> new ResourceNotFoundExceptionById("Chat", "chatId", chatId));
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", username));

		Message message = new Message();
		message.setContent(messageDto.getContent());
		message.setImage(messageDto.getImage());
		message.setTime(LocalDateTime.now());
		message.setChat(chat);
		message.setUser(user);

		Message save = messageRepository.save(message);
		MessageDto saveMessageDto = this.modelMapper.map(save, MessageDto.class);
		return saveMessageDto;
	}

	@Override
	public List<MessageDto> findChatsMessage(Long chatId) {
		this.chatRepository.findById(chatId)
				.orElseThrow(() -> new ResourceNotFoundExceptionById("Chat", "chatId", chatId));
		List<Message> messages = messageRepository.findByChatId(chatId);
		List<MessageDto> collect = messages.stream().map((message) -> this.modelMapper.map(message, MessageDto.class))
				.collect(Collectors.toList());
		return collect;
	}

}
