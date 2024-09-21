package com.api.dtos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private Long id;

	@NotEmpty
	private String name;

	@NotEmpty(message = "Username is required")
	@Length(min = 3, max = 45, message = "Username must be between 3 and 45 characters")
	private String username;

	@NotEmpty(message = "Email is required")
	@Length(min = 3, max = 45, message = "Email must be between 3 and 45 characters")
	@Email(message = "Email must be valid")
	private String email;

	@NotEmpty(message = "Password is required")
	@Length(min = 6, message = "Password must be longer than 6 characters")
	private String password;

	private String profileImage;

	private Set<UserDto> following = new HashSet<>();

	private Set<UserDto> followers = new HashSet<>();

	private Set<PostDto> posts = new HashSet<>();

	private Set<ReelDto> reels = new HashSet<>();

	private Set<StoryDto> stories = new HashSet<>();

	private List<ChatDto> chats = new ArrayList<>();

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public String getEmail() {
		return email;
	}

	@JsonProperty
	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public Set<UserDto> getFollowing() {
		return following;
	}

	@JsonProperty
	public void setFollowing(Set<UserDto> following) {
		this.following = following;
	}

	@JsonIgnore
	public Set<UserDto> getFollowers() {
		return followers;
	}

	@JsonProperty
	public void setFollowers(Set<UserDto> followers) {
		this.followers = followers;
	}

}
