package com.api.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

	private Long id;

	private String content;

	private String image;

	private LocalDateTime time;
	
//	private User userDto;
	
//	private Chat chatDto;


}
