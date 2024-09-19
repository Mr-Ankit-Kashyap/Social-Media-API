package com.api.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReelDto {

	private Long Id;

	private String title;

	private String video;
	
	private LocalDateTime time;

	private List<UserDto> liked = new ArrayList<>();
	
	private Set<CommentDto> comments = new HashSet<>();

}
