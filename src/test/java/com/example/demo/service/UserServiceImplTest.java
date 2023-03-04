package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.entity.Phone;
import com.example.demo.entity.User;
import com.example.demo.exception.MailAlreadyExistsException;
import com.example.demo.jwtauth.JwtService;
import com.example.demo.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtService jwtService;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	public void testCreateUser() throws MailAlreadyExistsException {
		UserRequestDTO userDTO = new UserRequestDTO();
		userDTO.setName("Lucas Pirito");
		userDTO.setEmail("lpirito@dominio.cl");
		userDTO.setPassword("password");
		List<Phone> phones = new ArrayList<>();
		Phone phone = new Phone();
		phone.setNumber("123456789");
		phone.setCityCode("123");
		phone.setCountryCode("1");
		phones.add(phone);
		userDTO.setPhones(phones);

		when(userRepository.save(any(User.class)))
				.thenReturn(new User("randomId", userDTO.getName(), userDTO.getEmail(), userDTO.getPassword(),
						userDTO.getPhones(), LocalDateTime.now(), null, LocalDateTime.now(), true, "token"));

		User userCreated = userService.createUser(userDTO);

		assertEquals(userCreated.getEmail(), userDTO.getEmail());

		verify(userRepository).findByEmail(userDTO.getEmail());
		verify(userRepository).save(any(User.class));

		assertNotNull(userCreated);
	}
	
	@Test
	public void testUpdateUser() throws Exception {
        User user = new User();
        user.setId("RANDOMUUID");
        user.setName("Lucas Pirito");
        user.setEmail("lpirito@dominio.cl");
        user.setPassword("password");
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);
        user.setPhones(Collections.emptyList());

        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findById("RANDOMUUID")).thenReturn(optionalUser);

        when(passwordEncoder.encode("newpassword")).thenReturn("encodedpassword");

        when(jwtService.createToken(anyMap(), any())).thenReturn("newtoken");

        UserRequestDTO userDTO = new UserRequestDTO();
        userDTO.setName("Lucas Pirito Updated");
        userDTO.setPassword("newpassword");
        userDTO.setEmail("lpirito@dominio.cl");
        List<Phone> phones = new ArrayList<>();
        phones.add(new Phone("1234567890", "123", "456"));
        userDTO.setPhones(phones);

        User updatedUser = userService.updateUser("RANDOMUUID", userDTO);

        assertEquals("Lucas Pirito Updated", updatedUser.getName());
        assertEquals("encodedpassword", updatedUser.getPassword());
        assertEquals("lpirito@dominio.cl", updatedUser.getEmail());
        assertEquals(1, updatedUser.getPhones().size());
        assertEquals("1234567890", updatedUser.getPhones().get(0).getNumber());
        assertEquals("123", updatedUser.getPhones().get(0).getCityCode());
        assertEquals("456", updatedUser.getPhones().get(0).getCountryCode());
        assertEquals("newtoken", updatedUser.getToken());

        verify(userRepository).save(user);
	}
}
