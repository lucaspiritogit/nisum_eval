package com.example.demo.service;

import java.util.Optional;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.MailAlreadyExistsException;

public interface IUserService {
	User createUser(UserRequestDTO userDTO) throws MailAlreadyExistsException;
	
	User updateUser(String id, UserRequestDTO userDTO) throws Exception;
	
	Optional<User> findUserByEmail(String email);
	
	Optional<User> findUserById(String id);
	
	String generateId();
}
