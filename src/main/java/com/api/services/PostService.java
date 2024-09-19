package com.api.services;

import java.util.List;

import com.api.dtos.PostDto;

public interface PostService {
	
	PostDto createPost(PostDto postDto , String username);
	
	String deletePost(Long postId , String username);
	
	PostDto findPostById(Long postId);
	
	List<PostDto> findPostByUsername(String username);
	
    List<PostDto> findAllPost();
    
    PostDto likePost(Long postId , String username);
    
    PostDto savePost(Long postId , String username);

}
