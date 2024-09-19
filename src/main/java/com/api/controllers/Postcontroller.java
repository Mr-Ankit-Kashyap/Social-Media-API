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

import com.api.dtos.PostDto;
import com.api.dtos.UserDto;
import com.api.services.PostService;
import com.api.services.UserService;

@RestController
@RequestMapping("/api/posts")
public class Postcontroller {

	private PostService postService;

	private UserService userService;

	public Postcontroller(PostService postService, UserService userService) {
		this.postService = postService;
		this.userService = userService;
	}

	@PostMapping("/")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @RequestHeader("Authorization") String jwt) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		PostDto createPost = this.postService.createPost(postDto, userDto.getUsername());
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<String> deletePost(@PathVariable Long postId, @RequestHeader("Authorization") String jwt) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		String deletePost = this.postService.deletePost(postId, userDto.getUsername());
		return new ResponseEntity<String>(deletePost, HttpStatus.OK);
	}

	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> findPostById(@PathVariable Long postId) {
		PostDto postDto = this.postService.findPostById(postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}

	@GetMapping("/user/{username}")
	public ResponseEntity<List<PostDto>> findPostByUserId(@PathVariable String username) {
		List<PostDto> postByUsername = this.postService.findPostByUsername(username);
		return new ResponseEntity<List<PostDto>>(postByUsername, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<PostDto>> findPosts() {
		List<PostDto> allPost = this.postService.findAllPost();
		return new ResponseEntity<List<PostDto>>(allPost, HttpStatus.OK);
	}

	@PutMapping("/like/{postId}")
	public ResponseEntity<PostDto> likePost(@PathVariable Long postId, @RequestHeader("Authorization") String jwt) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		PostDto postDto = postService.likePost(postId, userDto.getUsername());
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}

	@PutMapping("/save/{postId}")
	public ResponseEntity<PostDto> savePost(@PathVariable Long postId, @RequestHeader("Authorization") String jwt) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		PostDto postDto = postService.savePost(postId, userDto.getUsername());
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}

}
