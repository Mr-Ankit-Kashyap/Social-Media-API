package com.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.dtos.MessageDto;
import com.api.dtos.UserDto;
import com.api.services.MessageService;
import com.api.services.UserService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

	private MessageService messageService;

	private UserService userService;

	public MessageController(MessageService messageService, UserService userService) {
		this.messageService = messageService;
		this.userService = userService;
	}

	@PostMapping("/chat/{chatId}")
	public ResponseEntity<MessageDto> createMessage(@RequestBody MessageDto messageDto,
			@RequestHeader("Authorization") String jwt, @PathVariable Long chatId) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		MessageDto message = this.messageService.createMessage(messageDto, userDto.getUsername(), chatId);
		return new ResponseEntity<MessageDto>(message, HttpStatus.CREATED);
	}

	@GetMapping("/chat/{chatId}")
	public ResponseEntity<List<MessageDto>> findChatsMessage(@RequestHeader("Authorization") String jwt,
			@PathVariable Long chatId) {
		this.userService.findUserByJwt(jwt);
		List<MessageDto> chatsMessage = this.messageService.findChatsMessage(chatId);
		return new ResponseEntity<List<MessageDto>>(chatsMessage, HttpStatus.CREATED);
	}

}
