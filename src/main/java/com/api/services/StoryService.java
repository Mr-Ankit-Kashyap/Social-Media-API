package com.api.services;

import java.util.List;

import com.api.dtos.StoryDto;

public interface StoryService {

	StoryDto createStory(StoryDto storyDto, String username);

	List<StoryDto> findStoryByUsername(String username);

	String deleteStory(Long storyId, String username);

	StoryDto likeStory(Long storyId, String username);

}
