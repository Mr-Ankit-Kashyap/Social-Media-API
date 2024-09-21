package com.api.services;

import java.util.List;

import com.api.dtos.MessageDto;

public interface MessageService {

	MessageDto createMessage(MessageDto messageDto, String username, Long chatId);

	List<MessageDto> findChatsMessage(Long chatId);

}
