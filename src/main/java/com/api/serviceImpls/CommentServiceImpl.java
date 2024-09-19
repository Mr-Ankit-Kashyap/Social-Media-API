package com.api.serviceImpls;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.api.dtos.CommentDto;
import com.api.entities.Comment;
import com.api.entities.Post;
import com.api.entities.Reel;
import com.api.entities.User;
import com.api.exceptions.ResourceNotFoundExceptionById;
import com.api.exceptions.ResourceNotFoundExceptionByUsername;
import com.api.repositories.CommentRepository;
import com.api.repositories.PostRepository;
import com.api.repositories.ReelRepository;
import com.api.repositories.UserRepository;
import com.api.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private UserRepository userRepository;

	private PostRepository postRepository;

	private CommentRepository commentRepository;

	private ModelMapper modelMapper;

	private ReelRepository reelRepository;

	public CommentServiceImpl(UserRepository userRepository, PostRepository postRepository,
			CommentRepository commentRepository, ModelMapper modelmapper, ReelRepository reelRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
		this.modelMapper = modelmapper;
		this.reelRepository = reelRepository;
	}

	@Override
	public CommentDto createPostComment(CommentDto commentDto, String username, Long postId) {

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", username));

		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundExceptionById("Post", "postId", postId));

		Comment comment = new Comment();
		comment.setMessage(commentDto.getMessage());
		comment.setTime(LocalDateTime.now());
		comment.setUser(user);
		comment.setPost(post);
		Comment save = commentRepository.save(comment);
		CommentDto saveCommentDto = this.modelMapper.map(save, CommentDto.class);

		return saveCommentDto;
	}

	@Override
	public String deleteComment(Long commentId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundExceptionById("Comment", "commentId", commentId));
		commentRepository.delete(comment);
		return "Comment have deleted successfully ";
	}

	@Override
	public CommentDto findCommentById(Long commentId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundExceptionById("Comment", "commentId", commentId));
		CommentDto commentDto = this.modelMapper.map(comment, CommentDto.class);
		return commentDto;
	}

	@Override
	public CommentDto LikedComment(Long commentId, String username) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundExceptionById("Comment", "commentId", commentId));

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", username));

		if (comment.getLiked().contains(user)) {
			comment.getLiked().remove(user);
		} else {
			comment.getLiked().add(user);
		}

		Comment save = commentRepository.save(comment);

		CommentDto commentDto = this.modelMapper.map(save, CommentDto.class);

		return commentDto;
	}

	@Override
	public CommentDto createReelComment(CommentDto commentDto, String username, Long reelId) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", username));

		Reel reel = this.reelRepository.findById(reelId)
				.orElseThrow(() -> new ResourceNotFoundExceptionById("Reel", "reelId", reelId));

		Comment comment = new Comment();
		comment.setMessage(commentDto.getMessage());
		comment.setTime(LocalDateTime.now());
		comment.setUser(user);
		comment.setReel(reel);
		Comment save = commentRepository.save(comment);

		CommentDto saveCommentDto = this.modelMapper.map(save, CommentDto.class);

		return saveCommentDto;

	}

}
