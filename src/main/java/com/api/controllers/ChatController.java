package com.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseEntity<ChatDto> createChat(@RequestParam String chatUsername,
			@RequestHeader("Authorization") String jwt) {
		UserDto reqUserDto = this.userService.findUserByJwt(jwt);
		this.userService.findUserByUsername(chatUsername);
		ChatDto chatDto = this.chatService.createChat(reqUserDto.getUsername(), chatUsername);
		return new ResponseEntity<ChatDto>(chatDto, HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity<List<ChatDto>> findChat(@RequestHeader("Authorization") String jwt) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		List<ChatDto> chats = this.chatService.findUsersChat(userDto.getUsername());
		return new ResponseEntity<List<ChatDto>>(chats, HttpStatus.CREATED);
	}

	@GetMapping("/{chatId}")
	public ResponseEntity<ChatDto> createChat(@RequestHeader("Authorization") String jwt, @PathVariable Long chatId) {
		this.userService.findUserByJwt(jwt);
		ChatDto chatDto = this.chatService.findChatById(chatId);
		return new ResponseEntity<ChatDto>(chatDto, HttpStatus.CREATED);
	}

}
