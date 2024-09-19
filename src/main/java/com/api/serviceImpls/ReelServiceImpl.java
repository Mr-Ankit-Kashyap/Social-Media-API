package com.api.serviceImpls;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.api.dtos.ReelDto;
import com.api.entities.Reel;
import com.api.entities.User;
import com.api.exceptions.ResourceNotFoundExceptionById;
import com.api.exceptions.ResourceNotFoundExceptionByUsername;
import com.api.repositories.ReelRepository;
import com.api.repositories.UserRepository;
import com.api.services.ReelService;

@Service
public class ReelServiceImpl implements ReelService {

	private ReelRepository reelRepository;

	private UserRepository userRepository;

	private ModelMapper modelMapper;

	public ReelServiceImpl(ReelRepository reelRepository, UserRepository userRepository, ModelMapper modelMapper) {
		this.reelRepository = reelRepository;
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public ReelDto createReel(ReelDto reelDto, String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", username));

		Reel newReel = new Reel();
		newReel.setTitle(reelDto.getTitle());
		newReel.setVideo(reelDto.getVideo());
		newReel.setTime(LocalDateTime.now());
		newReel.setUser(user);

		Reel saveReel = this.reelRepository.save(newReel);
		ReelDto saveReelDto = this.modelMapper.map(saveReel, ReelDto.class);
		return saveReelDto;
	}

	@Override
	public List<ReelDto> findAllReels() {
		List<Reel> reels = reelRepository.findAll();
		List<ReelDto> reelDtos = reels.stream().map((reel) -> this.modelMapper.map(reel, ReelDto.class))
				.collect(Collectors.toList());
		return reelDtos;
	}

	@Override
	public List<ReelDto> findAllReelByUser(String username) {
		List<Reel> reels = reelRepository.findReelsByUsername(username);
		List<ReelDto> reelDtos = reels.stream().map((reel) -> this.modelMapper.map(reel, ReelDto.class))
				.collect(Collectors.toList());
		return reelDtos;
	}

	@Override
	public String deleteReel(Long reelId , String username ) {
		Reel reel = reelRepository.findById(reelId)
				.orElseThrow(() -> new ResourceNotFoundExceptionById("Reel", "reelId", reelId));
		
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", username));

		if (reel.getUser().getId() != user.getId()) {
			throw new RuntimeException("you can not delete	another users reel");
		}

		reelRepository.delete(reel);
		
		return "Story delete successfully";
	}

	@Override
	public ReelDto likeReel(Long reelId, String username) {
		Reel reel = reelRepository.findById(reelId)
				.orElseThrow(() -> new ResourceNotFoundExceptionById("Reel", "reelId", reelId));
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", username));

		if (reel.getLiked().contains(user)) {
			reel.getLiked().remove(user);
		} else {
			reel.getLiked().add(user);
		}

		Reel save = reelRepository.save(reel);
		ReelDto reelDto = this.modelMapper.map(save, ReelDto.class);
		return reelDto;

	}

	@Override
	public ReelDto saveReel(Long reelId, String username) {
		Reel reel = reelRepository.findById(reelId)
				.orElseThrow(() -> new ResourceNotFoundExceptionById("Reel", "reelId", reelId));
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", username));

		if (user.getSavedReel().contains(reel)) {
			user.getSavedReel().remove(reel);
		} else {
			user.getSavedReel().add(reel);
		}
		userRepository.save(user);
		ReelDto reelDto = this.modelMapper.map(reel, ReelDto.class);
		return reelDto;

	}

}
