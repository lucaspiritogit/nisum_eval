package com.example.demo.exception;

import org.springframework.security.authentication.AuthenticationServiceException;

public class MailNotFoundException extends AuthenticationServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MailNotFoundException() {
		super("La direccion de correo electronico proveida no existe.");
	}
}
