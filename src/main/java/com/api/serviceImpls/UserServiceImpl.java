package com.api.serviceImpls;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.dtos.UserDto;
import com.api.entities.User;
import com.api.exceptions.ResourceNotFoundExceptionByUsername;
import com.api.repositories.UserRepository;
import com.api.securities.JwtHelper;
import com.api.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	private ModelMapper modelMapper;

	private PasswordEncoder passwordEncoder;

	private JwtHelper jwtHelper;

	
	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder,
			JwtHelper jwtHelper) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
		this.jwtHelper = jwtHelper;
	}

	@Override
	public UserDto registerUser(UserDto userDto) {
		if (userRepository.findByUsername(userDto.getUsername()).isPresent()
				|| userRepository.findByEmail(userDto.getEmail()).isPresent()) {
			throw new RuntimeException("User already exist");
		}

		User newUser = new User();

		newUser.setName(userDto.getName());
		newUser.setUsername(userDto.getUsername());
		newUser.setEmail(userDto.getEmail());
		newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
		User save = userRepository.save(newUser);
		UserDto saveUserDto = this.modelMapper.map(save, UserDto.class);

		return saveUserDto;
	}

	@Override
	public UserDto findUserByUsername(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", username));

		UserDto userDto = this.modelMapper.map(user, UserDto.class);

		return userDto;

	}

	@Override
	public String deleteUserByUsername(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", username));
		userRepository.delete(user);

		return " You have deleted successfully ";
	}

	@Override
	public String updateUserByUsername(User userDto, String username) {
		User existUser = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", username));
		existUser.setName(userDto.getName());
		existUser.setUsername(userDto.getUsername());
		existUser.setEmail(userDto.getEmail());
		existUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
		existUser.setProfileImage(userDto.getProfileImage());
		existUser.setGender(userDto.getGender());
		existUser.setPhonenumber(userDto.getPhonenumber());
		existUser.setBio(userDto.getBio());
		existUser.setCountry(userDto.getCountry());
		userRepository.save(existUser);
		return "You have updated Successfully";
	}

	@Override
	public List<UserDto> findAllUser() {
		List<User> users = userRepository.findAll();
		List<UserDto> userDtos = users.stream().map((user) -> this.modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		return userDtos;

	}

	@Override
	public List<UserDto> searchUser(String query) {
		List<User> users = userRepository.searchUser(query);
		List<UserDto> userDtos = users.stream().map((user) -> this.modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		return userDtos;

	}

	@Override
	public String followUser(String followerUsername, String followingUsername) {
		User follower = userRepository.findByUsername(followerUsername)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", followerUsername));
		User following = userRepository.findByUsername(followingUsername)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", followingUsername));

		if (follower.getFollowing().contains(following)) {
			throw new RuntimeException("User is already following this user");
		}

		follower.follow(following);

		userRepository.save(follower);
		userRepository.save(following);

		return "You have followed " + following.getUsername();

	}

	@Override
	public String unFollowUser(String followerUsername, String followingUsername) {
		User follower = userRepository.findByUsername(followerUsername)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", followerUsername));
		User following = userRepository.findByUsername(followingUsername)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", followingUsername));

		if (!follower.getFollowing().contains(following)) {
			throw new RuntimeException("User is not following this user");

		}

		follower.unfollow(following);

		userRepository.save(follower);
		userRepository.save(following);

		return "You have unFollowed " + following.getUsername();
	}

	@Override
	public Set<UserDto> getFollowers(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", username));

		UserDto userDto = this.modelMapper.map(user, UserDto.class);

		Set<UserDto> followers = userDto.getFollowers();

		return followers;
	}

	@Override
	public Set<UserDto> getFollowing(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", username));

		UserDto userDto = this.modelMapper.map(user, UserDto.class);

		Set<UserDto> followings = userDto.getFollowing();

		return followings;
	}

	@Override
	public UserDto findUserByJwt(String jwt) {

		String username = this.jwtHelper.findUsernameFromToken(jwt);

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundExceptionByUsername("User", "username", username));
		
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		
		return userDto;

	}

}
