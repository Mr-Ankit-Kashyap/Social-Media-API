package com.api.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.dtos.UserDto;
import com.api.entities.User;
import com.api.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/{username}")
	public ResponseEntity<UserDto> findByUsername(@PathVariable String username,
			@RequestHeader("Authorization") String jwt) {
		UserDto userDto = this.userService.findUserByUsername(username);
		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
	}

	@PutMapping("/")
	public ResponseEntity<String> updateByUsername(@Valid @RequestBody User userDto,
			@RequestHeader("Authorization") String jwt) {
		UserDto user = this.userService.findUserByJwt(jwt);
		String updateUserByUsername = this.userService.updateUserByUsername(userDto, user.getUsername());
		return new ResponseEntity<String>(updateUserByUsername, HttpStatus.OK);
	}

	@DeleteMapping("/")
	public ResponseEntity<String> deleteByUsername(@RequestHeader("Authorization") String jwt) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		String deleteUserByUsername = userService.deleteUserByUsername(userDto.getUsername());
		return new ResponseEntity<String>(deleteUserByUsername, HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<List<UserDto>> searchUser(@RequestParam("query") String query) {
		List<UserDto> searchUser = this.userService.searchUser(query);
		return new ResponseEntity<List<UserDto>>(searchUser, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser() {
		List<UserDto> users = this.userService.findAllUser();
		return new ResponseEntity<List<UserDto>>(users, HttpStatus.OK);
	}

	@PutMapping("/follow/{followingUsername}")
	public ResponseEntity<String> followUser(@RequestHeader("Authorization") String jwt,
			@PathVariable String followingUsername) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		String followUser = userService.followUser(userDto.getUsername(), followingUsername);
		return new ResponseEntity<String>(followUser, HttpStatus.OK);
	}

	@PutMapping("/unfollow/{followingUsername}")
	public ResponseEntity<String> unFollowUser(@RequestHeader("Authorization") String jwt,
			@PathVariable String followingUsername) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		String unFollowUser = userService.unFollowUser(userDto.getUsername(), followingUsername);
		return new ResponseEntity<>(unFollowUser, HttpStatus.OK);
	}

	@GetMapping("/followers")
	public ResponseEntity<Set<UserDto>> findfollowersByUsername(@RequestHeader("Authorization") String jwt) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		Set<UserDto> followers = this.userService.getFollowers(userDto.getUsername());
		return new ResponseEntity<Set<UserDto>>(followers, HttpStatus.OK);
	}

	@GetMapping("/following")
	public ResponseEntity<Set<UserDto>> findfollowingByUsername(@RequestHeader("Authorization") String jwt) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		Set<UserDto> following = this.userService.getFollowing(userDto.getUsername());
		return new ResponseEntity<Set<UserDto>>(following, HttpStatus.OK);
	}

	@GetMapping("/profile")
	public ResponseEntity<UserDto> getUserFromToken(@RequestHeader("Authorization") String jwt) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
	}

}
