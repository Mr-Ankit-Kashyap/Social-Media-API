package com.api.serviceImpls;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.api.dtos.StoryDto;
import com.api.entities.Story;
import com.api.entities.User;
import com.api.exceptions.ResourceNotFoundExceptionById;
import com.api.exceptions.ResourceNotFoundExceptionByUsername;
import com.api.repositories.StoryRepository;
import com.api.repositories.UserRepository;
import com.api.services.StoryService;


@Service
public class StoryServiceImpl implements StoryService {

	private StoryRepository storyRepository;

	private UserRepository userRepository;

	private ModelMapper modelMapper;

	public StoryServiceImpl(StoryRepository storyRepository, UserRepository userRepository, ModelMapper modelMapper) {
		this.storyRepository = storyRepository;
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public StoryDto createStory(StoryDto storyDto, String username) {

		User user = this.userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", username));
		Story newStory = new Story();
		newStory.setCaption(storyDto.getCaption());
		newStory.setImage(storyDto.getImage());
		newStory.setTime(LocalDateTime.now());
		newStory.setUser(user);
		Story story = this.storyRepository.save(newStory);
		StoryDto saveStoryDto = this.modelMapper.map(story, StoryDto.class);
		return saveStoryDto;
	}

	@Override
	public List<StoryDto> findStoryByUsername(String username) {
		List<Story> stories = this.storyRepository.findStoryByUsername(username);
		List<StoryDto> storyDto = stories.stream().map((story) -> this.modelMapper.map(story, StoryDto.class))
				.collect(Collectors.toList());
		return storyDto;
	}

	@Override
	public String deleteStory(Long storyId, String username) {

		Story story = storyRepository.findById(storyId)
				.orElseThrow(() -> new ResourceNotFoundExceptionById("Story", "storyId", storyId));

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", username));

		if (story.getUser().getId() != user.getId()) {
			throw new RuntimeException("you can not delete	another users story");
		}

		storyRepository.delete(story);

		return "Story delete successfully";
	}

	@Override
	public StoryDto likeStory(Long storyId, String username) {
		Story story = storyRepository.findById(storyId)
				.orElseThrow(() -> new ResourceNotFoundExceptionById("Story", "storyId", storyId));
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", username));

		if (story.getLiked().contains(user)) {
			story.getLiked().remove(user);
		} else {
			story.getLiked().add(user);
		}

		Story saveStory = storyRepository.save(story);
		StoryDto storyDto = this.modelMapper.map(saveStory, StoryDto.class);
		return storyDto;
	}

}
