package com.api.services;

import com.api.dtos.CommentDto;

public interface CommentService {

	CommentDto createPostComment(CommentDto commentDto, String username, Long postId);

	String deleteComment(Long commentId);

	CommentDto findCommentById(Long commentId);

	CommentDto LikedComment(Long commentId, String username);

	CommentDto createReelComment(CommentDto commentDto, String username, Long reelId);

}
