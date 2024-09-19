package com.api.services;

import java.util.List;
import java.util.Set;

import com.api.dtos.UserDto;
import com.api.entities.User;



public interface UserService {

	UserDto registerUser(UserDto userDto);

	UserDto findUserByUsername(String username);

	String updateUserByUsername(User user, String username);

	String deleteUserByUsername(String username);

	List<UserDto> findAllUser();

	List<UserDto> searchUser(String query);

	String followUser(String followerUsername, String followingUsername);

	String unFollowUser(String followerUsername, String followingUsername);

	Set<UserDto> getFollowers(String username);

	Set<UserDto> getFollowing(String username);

	UserDto findUserByJwt(String jwt);

}
