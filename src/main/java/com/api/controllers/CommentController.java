package com.api.controllers;

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

import com.api.dtos.CommentDto;
import com.api.dtos.UserDto;
import com.api.services.CommentService;
import com.api.services.UserService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	private UserService userService;

	private CommentService commentService;

	public CommentController(UserService userService, CommentService commentService) {
		this.userService = userService;
		this.commentService = commentService;
	}

	@PostMapping("/posts/{postId}")
	public ResponseEntity<CommentDto> createPostComment(@RequestBody CommentDto commentDto,
			@RequestHeader("Authorization") String jwt, @PathVariable Long postId) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		CommentDto comment = commentService.createPostComment(commentDto, userDto.getUsername(), postId);
		return new ResponseEntity<CommentDto>(comment, HttpStatus.CREATED);
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<String> deleteComment(@RequestHeader("Authorization") String jwt,
			@PathVariable Long commentId) {
		this.userService.findUserByJwt(jwt);
		String comment = commentService.deleteComment(commentId);
		return new ResponseEntity<String>(comment, HttpStatus.CREATED);
	}

	@GetMapping("/{commentId}")
	public ResponseEntity<CommentDto> findCommentById(@RequestHeader("Authorization") String jwt,
			@PathVariable Long commentId) {
		this.userService.findUserByJwt(jwt);
		CommentDto commentDto = commentService.findCommentById(commentId);
		return new ResponseEntity<CommentDto>(commentDto, HttpStatus.CREATED);
	}

	@PutMapping("/{commentId}")
	public ResponseEntity<CommentDto> likeComment(@RequestHeader("Authorization") String jwt,
			@PathVariable Long commentId) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		CommentDto commentDto = commentService.LikedComment(commentId, userDto.getUsername());
		return new ResponseEntity<CommentDto>(commentDto, HttpStatus.CREATED);
	}

	@PostMapping("/reels/{reelId}")
	public ResponseEntity<CommentDto> createReelComment(@RequestBody CommentDto commentDto,
			@RequestHeader("Authorization") String jwt, @PathVariable Long postId) {
		UserDto userDto = this.userService.findUserByJwt(jwt);
		CommentDto comment = commentService.createReelComment(commentDto, userDto.getUsername(), postId);
		return new ResponseEntity<CommentDto>(comment, HttpStatus.CREATED);
	}

}
