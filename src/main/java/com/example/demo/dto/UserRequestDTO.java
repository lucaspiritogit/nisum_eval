package com.example.demo.dto;

import java.util.List;

import com.example.demo.entity.Phone;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserRequestDTO {

	@NotNull(message = "El nombre no puede ser null")
	@NotBlank(message = "El nombre no puede estar vacio")
	private String name;

	@NotNull(message = "La password no puede ser null")
	@NotBlank(message = "La password no puede estar vacia")
	private String password;

	@NotNull(message = "El email no puede ser null")
	@NotBlank(message = "El email no puede estar vacio")
	private String email;

	private List<Phone> phones;

	public UserRequestDTO() {

	}

	public UserRequestDTO(String name, String password, String email, List<Phone> phones) {
		super();
		this.name = name;
		this.password = password;
		this.email = email;
		this.phones = phones;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

}
