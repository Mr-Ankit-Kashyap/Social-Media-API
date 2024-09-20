package com.api.services;

import java.util.List;

import com.api.dtos.ChatDto;
import com.api.dtos.UserDto;

public interface ChatService {
	
	ChatDto createChat(UserDto reqUserDto , UserDto chatUserDto);
	
	ChatDto findChatById(Long chatId);
	
	List<ChatDto> findUsersChat(String username);

}
