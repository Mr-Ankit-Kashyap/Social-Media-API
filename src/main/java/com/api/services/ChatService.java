package com.api.services;

import java.util.List;

import com.api.dtos.ChatDto;

public interface ChatService {
	
	ChatDto createChat(String reqUsername , String  chatUsername);
	
	ChatDto findChatById(Long chatId);
	
	List<ChatDto> findUsersChat(String username);

}
