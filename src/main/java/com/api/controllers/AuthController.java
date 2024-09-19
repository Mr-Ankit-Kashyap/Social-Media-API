package com.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.dtos.UserDto;
import com.api.exceptions.ApiException;
import com.api.securities.JwtHelper;
import com.api.securities.JwtRequest;
import com.api.securities.JwtResponse;
import com.api.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private JwtHelper helper;

	private UserDetailsService userDetailsService;

	private AuthenticationManager manager;

	private UserService userService;

	public AuthController(JwtHelper helper, UserDetailsService userDetailsService, AuthenticationManager manager,
			UserService userService) {
		this.helper = helper;
		this.userDetailsService = userDetailsService;
		this.manager = manager;
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@Valid @RequestBody JwtRequest request) {

		this.doAuthenticate(request.getUsername(), request.getPassword());

		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.helper.generateToken(userDetails);

		JwtResponse response = JwtResponse.builder().jwtToken(token).username(userDetails.getUsername()).build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private void doAuthenticate(String username, String password) {

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
				password);
		try {
			manager.authenticate(authentication);

		} catch (BadCredentialsException e) {
			throw new ApiException(" Invalid Username or Password  !!");
		}

	}

	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
		UserDto registerUser = this.userService.registerUser(userDto);
		return new ResponseEntity<UserDto>(registerUser, HttpStatus.CREATED);
	}

}
