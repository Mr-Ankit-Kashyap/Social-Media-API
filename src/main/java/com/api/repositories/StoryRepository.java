package com.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.entities.Story;

public interface StoryRepository extends JpaRepository<Story, Long> {
	
	@Query("SELECT s FROM Story s WHERE s.user.username=:username")
	List<Story> findStoryByUsername(String username);

}
