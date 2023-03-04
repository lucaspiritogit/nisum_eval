package com.example.demo.exceptionhandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.exception.InvalidEmailFormatException;
import com.example.demo.exception.MailAlreadyExistsException;
import com.example.demo.exception.MailNotFoundException;

@RestControllerAdvice
public class UserAuthControllerAdvice {
	
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Map<String, String> handleException(Exception ex) {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("mensaje", "Un error inesperado ha ocurrido");
		// This should be a logger, just using sysout as an example
		System.out.println(ex.getMessage());
		return errorResponse;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(MailAlreadyExistsException.class)
	public Map<String, String> handleMailAlreadyFoundException(MailAlreadyExistsException ex) {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("mensaje", ex.getMessage());
		return errorResponse;
	}
    
	@ResponseBody
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(BadCredentialsException.class)
	public Map<String, String> handleBadCredentialException(BadCredentialsException ex) {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("mensaje", ex.getMessage());
		return errorResponse;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidEmailFormatException.class)
	public Map<String, String> handleInvalidEmailFormat(InvalidEmailFormatException ex) {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("mensaje", ex.getMessage());
		return errorResponse;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(MailNotFoundException.class)
	public Map<String, String> handleInternalAuthenticationServiceException(MailNotFoundException ex) {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("mensaje", ex.getMessage());
		return errorResponse;
	}
}
