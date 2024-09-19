package com.api.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoryDto {

	private Long id;

	private String caption;

	private String image;

	private LocalDateTime time;

	private List<UserDto> liked = new ArrayList<>();

}
