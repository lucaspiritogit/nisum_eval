package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;

import com.example.demo.entity.Phone;
import com.example.demo.entity.User;

public class UserResponseDTO {

	private String id;
	
	private String name;
	
	private String password;
	
	private String email;
	
	private List<Phone> phones;
	
    @CreationTimestamp
    private LocalDateTime created;
	
	private LocalDateTime modified;
	
    @CreationTimestamp
	private LocalDateTime lastLogin;
	
	private String token;
	
	private boolean isActive;
	
	public UserResponseDTO(Optional<User> user) {
		this.id = user.get().getId();
		this.created = user.get().getCreated();
		this.modified = user.get().getModified();
		this.lastLogin = user.get().getLastLogin();
		this.token = user.get().getToken();
		this.isActive = user.get().isActive();
		this.name = user.get().getName();
		this.password = user.get().getPassword();
		this.email = user.get().getEmail();
		this.phones = user.get().getPhones();
	}

	public UserResponseDTO(User user) {
		this.id = user.getId();
		this.created = user.getCreated();
		this.modified = user.getModified();
		this.lastLogin = user.getLastLogin();
		this.token = user.getToken();
		this.isActive = user.isActive();
		this.name = user.getName();
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.phones = user.getPhones();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getModified() {
		return modified;
	}

	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	
	
	
}
