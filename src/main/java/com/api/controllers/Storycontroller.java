package com.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.dtos.StoryDto;
import com.api.dtos.UserDto;
import com.api.services.StoryService;
import com.api.services.UserService;

@RestController
@RequestMapping("/api/stories")
public class Storycontroller {

	private StoryService storyService;

	private UserService userService;

	public Storycontroller(StoryService storyService, UserService userService) {
		this.storyService = storyService;
		this.userService = userService;
	}

	@PostMapping("/")
	public ResponseEntity<StoryDto> createStory(@RequestBody StoryDto storyDto,
			@RequestHeader("Authorization") String jwt) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		StoryDto story = this.storyService.createStory(storyDto, userDto.getUsername());
		return new ResponseEntity<StoryDto>(story, HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity<List<StoryDto>> findAllStory(@RequestHeader("Authorization") String jwt) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		List<StoryDto> storyByUsername = this.storyService.findStoryByUsername(userDto.getUsername());
		return new ResponseEntity<List<StoryDto>>(storyByUsername, HttpStatus.OK);
	}

	@DeleteMapping("/{storyId}")
	public ResponseEntity<String> deleteStory(@PathVariable Long storyId, @RequestHeader("Authorization") String jwt) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		String deleteStory = this.storyService.deleteStory(storyId, userDto.getUsername());
		return new ResponseEntity<String>(deleteStory, HttpStatus.OK);
	}

	@PutMapping("/like/{storyId}")
	public ResponseEntity<StoryDto> likePost(@PathVariable Long reelId, @RequestHeader("Authorization") String jwt) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		StoryDto likeStory = storyService.likeStory(reelId, userDto.getUsername());
		return new ResponseEntity<StoryDto>(likeStory, HttpStatus.OK);
	}
}
