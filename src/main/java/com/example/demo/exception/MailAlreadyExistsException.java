package com.example.demo.exception;

public class MailAlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MailAlreadyExistsException() {
		super("La direccion de correo electronico proveida ya existe.");
	}
}
