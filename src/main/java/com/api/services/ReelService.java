package com.api.services;

import java.util.List;

import com.api.dtos.ReelDto;

public interface ReelService {
	
	ReelDto createReel(ReelDto reelDto , String username);
	
	List<ReelDto> findAllReels();
	
	List<ReelDto> findAllReelByUser(String username);
	
	String deleteReel(Long reelId ,String username);
	
	ReelDto likeReel(Long reelId , String username);
	
	ReelDto saveReel(Long reelId , String username);
}
