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

import com.api.dtos.ReelDto;
import com.api.dtos.UserDto;
import com.api.services.ReelService;
import com.api.services.UserService;

@RestController
@RequestMapping("/api/reels")
public class ReelController {

	private ReelService reelService;

	private UserService userService;

	public ReelController(ReelService reelService, UserService userService) {
		this.reelService = reelService;
		this.userService = userService;
	}

	@PostMapping("/")
	public ResponseEntity<ReelDto> createReel(@RequestBody ReelDto reelDto,
			@RequestHeader("Authorization") String jwt) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		ReelDto createDto = this.reelService.createReel(reelDto, userDto.getUsername());
		return new ResponseEntity<ReelDto>(createDto, HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity<List<ReelDto>> findAllReel(@RequestHeader("Authorization") String jwt) {
		this.userService.findUserByJwt(jwt);
		List<ReelDto> reels = this.reelService.findAllReels();
		return new ResponseEntity<List<ReelDto>>(reels, HttpStatus.OK);
	}

	@GetMapping("/users/{username}")
	public ResponseEntity<List<ReelDto>> findAllReelByUser(@RequestHeader("Authorization") String jwt,
			@PathVariable String username) {
		this.userService.findUserByJwt(jwt);
		List<ReelDto> reels = this.reelService.findAllReelByUser(username);
		return new ResponseEntity<List<ReelDto>>(reels, HttpStatus.OK);
	}

	@DeleteMapping("/{reelId}")
	public ResponseEntity<String> deleteReel(@RequestHeader("Authorization") String jwt, @PathVariable Long reelId) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		String deleteReel = this.reelService.deleteReel(reelId , userDto.getUsername());
		return new ResponseEntity<String>(deleteReel, HttpStatus.OK);
	}

	@PutMapping("/like/{reelId}")
	public ResponseEntity<ReelDto> likePost(@PathVariable Long reelId, @RequestHeader("Authorization") String jwt) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		ReelDto reelDto = reelService.likeReel(reelId, userDto.getUsername());
		return new ResponseEntity<ReelDto>(reelDto, HttpStatus.OK);
	}

	@PutMapping("/save/{reelId}")
	public ResponseEntity<ReelDto> savePost(@PathVariable Long reelId, @RequestHeader("Authorization") String jwt) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		ReelDto reelDto = reelService.saveReel(reelId, userDto.getUsername());
		return new ResponseEntity<ReelDto>(reelDto, HttpStatus.OK);
	}

}
