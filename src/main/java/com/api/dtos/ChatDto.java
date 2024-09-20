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
public class ChatDto {

	private Long id;

	private String chat_name;

	private String chat_image;

	private LocalDateTime time;

	List<UserDto> users = new ArrayList<>();

}
