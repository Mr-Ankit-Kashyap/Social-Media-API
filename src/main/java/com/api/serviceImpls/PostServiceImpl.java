package com.api.serviceImpls;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.api.dtos.PostDto;
import com.api.entities.Post;
import com.api.entities.User;
import com.api.exceptions.ResourceNotFoundExceptionById;
import com.api.exceptions.ResourceNotFoundExceptionByUsername;
import com.api.repositories.PostRepository;
import com.api.repositories.UserRepository;
import com.api.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;

	private UserRepository userRepository;

	private ModelMapper modelMapper;
	
    
	public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, ModelMapper modelMapper) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public PostDto createPost(PostDto postDto, String username) {

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", username));

		Post newPost = new Post();
		newPost.setCaption(postDto.getCaption());
		newPost.setImage(postDto.getImage());
		newPost.setTime(LocalDateTime.now());
		newPost.setVideo(postDto.getVideo());
		newPost.setUser(user);
		Post post= this.postRepository.save(newPost);
		
		PostDto savePostDto = this.modelMapper.map(post, PostDto.class);
		return savePostDto;
		
	}

	@Override
	public String deletePost(Long postId, String username) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundExceptionById("Post", "postId", postId));
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", username));

		if (post.getUser().getId() != user.getId()) {
			throw new RuntimeException("you can not delete	another users post");
		}

		postRepository.delete(post);

		return "Post have deleted successfully ";
	}

	@Override
	public PostDto findPostById(Long postId) {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundExceptionById("Post", "postId", postId));
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	public List<PostDto> findPostByUsername(String username) {
		List<Post> posts = this.postRepository.findPostsByUsername(username);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> findAllPost() {
		List<Post> posts = postRepository.findAll();
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;

	}

	@Override
	public PostDto likePost(Long postId, String username) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundExceptionById("Post", "postId", postId));
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", username));

		if (post.getLiked().contains(user)) {
			post.getLiked().remove(user);
		} else {
			post.getLiked().add(user);
		}

		Post save = postRepository.save(post);
		PostDto postDto = this.modelMapper.map(save, PostDto.class);
		return postDto;
	}

	@Override
	public PostDto savePost(Long postId, String username) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundExceptionById("Post", "postId", postId));
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", username));

		if (user.getSavedPost().contains(post)) {
			user.getSavedPost().remove(post);
		} else {
			user.getSavedPost().add(post);
		}
		userRepository.save(user);
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		return postDto;
	}

}
