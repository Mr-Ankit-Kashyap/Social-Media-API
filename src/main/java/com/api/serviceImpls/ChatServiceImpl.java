package com.api.serviceImpls;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.api.dtos.ChatDto;
import com.api.entities.Chat;
import com.api.entities.User;
import com.api.exceptions.ResourceNotFoundExceptionById;
import com.api.exceptions.ResourceNotFoundExceptionByUsername;
import com.api.repositories.ChatRepository;
import com.api.repositories.UserRepository;
import com.api.services.ChatService;

@Service
public class ChatServiceImpl implements ChatService {

	private ChatRepository chatRepository;

	private ModelMapper modelMapper;

	private UserRepository userRepository;

	public ChatServiceImpl(ChatRepository chatRepository, ModelMapper modelMapper) {
		this.chatRepository = chatRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public ChatDto createChat(String reqUsername, String chatUsername) {
		User reqUser = userRepository.findByUsername(reqUsername)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", reqUsername));
		User chatUser = userRepository.findByUsername(chatUsername)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", chatUsername));

		Chat isExist = this.chatRepository.findChatByUser(reqUser, chatUser);
		if (isExist != null) {
			ChatDto chatDto = this.modelMapper.map(isExist, ChatDto.class);
			return chatDto;
		}

		Chat chat = new Chat();
		chat.setUsername(reqUsername);
		chat.setChatUsername(chatUsername);
		chat.setChat_image(chatUser.getProfileImage());
		chat.setTime(LocalDateTime.now());

		Chat save = chatRepository.save(chat);
		ChatDto chatDto = this.modelMapper.map(save, ChatDto.class);
		return chatDto;
	}

	@Override
	public List<ChatDto> findUsersChat(String username) {		
		List<Chat> chats = chatRepository.findChatsByUsername(username);
		List<ChatDto> chatDto = chats.stream().map((chat) -> this.modelMapper.map(chat, ChatDto.class))
				.collect(Collectors.toList());
		return chatDto;

	}

	@Override
	public ChatDto findChatById(Long chatId) {
		Chat chat = this.chatRepository.findById(chatId)
				.orElseThrow(() -> new ResourceNotFoundExceptionById("Chat", "chatId", chatId));
		ChatDto chatDto = this.modelMapper.map(chat, ChatDto.class);
		return chatDto;
	}

}
