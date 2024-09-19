package com.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	
   @Query("SELECT p FROM Post p WHERE p.user.username=:username")	
   List<Post> findPostsByUsername(String username);
   
}
