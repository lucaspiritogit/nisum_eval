package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserServiceImpl;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	UserServiceImpl userService;

	@Autowired
	AuthenticationManager authManager;

	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> getUser(@PathVariable String id) {
		Optional<User> user = userService.findUserById(id);
		return ResponseEntity.ok().body(new UserResponseDTO(user));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserResponseDTO> editUser(@PathVariable String id, @RequestBody UserRequestDTO userDTO)
			throws Exception {

		User updatedUser = userService.updateUser(id, userDTO);
		return ResponseEntity.ok().body(new UserResponseDTO(updatedUser));
	}
}
