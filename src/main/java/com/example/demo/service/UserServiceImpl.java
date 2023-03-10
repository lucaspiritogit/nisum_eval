package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.entity.Phone;
import com.example.demo.entity.User;
import com.example.demo.exception.MailAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.jwtauth.JwtService;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	public User createUser(UserRequestDTO userDTO) throws MailAlreadyExistsException {
	    if(userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
	        throw new MailAlreadyExistsException();
	    }
		User userCreated = new User();
		userCreated.setName(userDTO.getName());
		userCreated.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		userCreated.setEmail(userDTO.getEmail());
		userCreated.setCreated(LocalDateTime.now());
		userCreated.setLastLogin(LocalDateTime.now());
		userCreated.setActive(true);
		
		List<Phone> phones = new ArrayList<>();
		for(Phone phone : userDTO.getPhones()) {
			phones.add(new Phone(phone.getNumber(), phone.getCityCode(), phone.getCountryCode()));
		}
		userCreated.setPhones(phones);
		
		String jwt = jwtService.createToken(new HashMap<>(), userCreated);
		userCreated.setToken(jwt);
		
		return userRepository.save(userCreated);
	}
	
	@Override
	public User updateUser(String id, UserRequestDTO userDTO) throws UserNotFoundException {
	    User user = findUserById(id).orElseThrow(() -> new UserNotFoundException());
	    user.setName(userDTO.getName());
	    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
	    user.setEmail(userDTO.getEmail());
	    List<Phone> phones = new ArrayList<>();
	    for(Phone phone : userDTO.getPhones()) {
	        phones.add(new Phone(phone.getNumber(), phone.getCityCode(), phone.getCountryCode()));
	    }
	    user.setPhones(phones);
		String jwt = jwtService.createToken(new HashMap<>(), user);
		user.setToken(jwt);
		user.setModified(LocalDateTime.now());
		userRepository.save(user);
		return user;
	}
	
	@Override
	public Optional<User> findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public Optional<User> findUserById(String id) {
		return userRepository.findById(id);
	}
}
