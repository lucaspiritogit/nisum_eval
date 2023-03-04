package com.example.demo.exception;

public class InvalidEmailFormatException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidEmailFormatException() {
		super("La direccion de correo electronico no tiene el formato correcto. El email no puede superar los 30 caracteres y debe utilizar @dominio.cl");
	}
	
}
