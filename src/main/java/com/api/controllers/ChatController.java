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

import com.api.dtos.ChatDto;
import com.api.dtos.UserDto;
import com.api.services.ChatService;
import com.api.services.UserService;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

	private ChatService chatService;

	private UserService userService;

	public ChatController(ChatService chatService, UserService userService) {
		this.chatService = chatService;
		this.userService = userService;
	}

	@PostMapping("/")
	public ResponseEntity<ChatDto> createChat(@RequestBody ChatDto chatDto,
			@RequestHeader("Authorization") String jwt) {
		UserDto reqUser = this.userService.findUserByJwt(jwt);
		ChatDto createChat = this.chatService.createChat(reqUser.getUsername(), chatDto.getChatUsername());
		return new ResponseEntity<ChatDto>(createChat, HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity<List<ChatDto>> findChat(@RequestHeader("Authorization") String jwt) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		List<ChatDto> chats = this.chatService.findUsersChat(userDto.getUsername());
		return new ResponseEntity<List<ChatDto>>(chats, HttpStatus.CREATED);
	}

	@GetMapping("/{chatId}")
	public ResponseEntity<ChatDto> getChat(@RequestHeader("Authorization") String jwt, @PathVariable Long chatId) {
		this.userService.findUserByJwt(jwt);
		ChatDto chatDto = this.chatService.findChatById(chatId);
		return new ResponseEntity<ChatDto>(chatDto, HttpStatus.CREATED);
	}

}
