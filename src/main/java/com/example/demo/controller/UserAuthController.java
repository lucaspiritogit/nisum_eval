package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.InvalidEmailFormatException;
import com.example.demo.exception.MailAlreadyExistsException;
import com.example.demo.exception.MailNotFoundException;
import com.example.demo.service.UserServiceImpl;

@RestController
@RequestMapping("/api/v1/auth")
public class UserAuthController {

	@Autowired
	UserServiceImpl userService;

	@Autowired
	AuthenticationManager authManager;
	
	@Value("${email-regexp}") // Found in application.properties
	private String emailRegexp;

	@PostMapping()
	public ResponseEntity<UserResponseDTO> authenticateUser(@RequestBody UserRequestDTO userDTO)
			throws BadCredentialsException {
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
		} catch (InternalAuthenticationServiceException e) {
			throw new MailNotFoundException();
		}
		
		Optional<User> user = userService.findUserByEmail(userDTO.getEmail());
		user.get().setLastLogin(LocalDateTime.now());
		return ResponseEntity.ok().body(new UserResponseDTO(user));

	}

	@PostMapping("/register")
	public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userDTO)
			throws MailAlreadyExistsException, InvalidEmailFormatException {
		
		if (!userDTO.getEmail().matches(emailRegexp)) {
			throw new InvalidEmailFormatException();
		} 
		
		User user = userService.createUser(userDTO);

		return ResponseEntity.ok().body(new UserResponseDTO(user));

	}

}
