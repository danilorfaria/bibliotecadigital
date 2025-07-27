package com.scraping.bibliotecadigital.resource.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.scraping.bibliotecadigital.services.exceptions.DataBaseException;
import com.scraping.bibliotecadigital.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

//import com.amazonaws.AmazonClientException;
//import com.amazonaws.AmazonServiceException;
//import com.devsuperior.dscatalog.Services.exceptions.DataBaseException;
//import com.devsuperior.dscatalog.Services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError error = new StandardError();
		
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Resource not found");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandardError> dataBase(DataBaseException e, HttpServletRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError();
		
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("DataBase exception");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationError error = new ValidationError();
		
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Validation exception");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		
		for (FieldError item : e.getBindingResult().getFieldErrors()) {
			
			error.addError(item.getField(), item.getDefaultMessage());
		}
		
		return ResponseEntity.status(status).body(error);
	}
	
//	@ExceptionHandler(AmazonServiceException.class)
//	public ResponseEntity<StandardError> AmazonService(AmazonServiceException e, HttpServletRequest request) {
//		
//		HttpStatus status = HttpStatus.BAD_REQUEST;
//		StandardError error = new StandardError();
//		
//		error.setTimestamp(Instant.now());
//		error.setStatus(status.value());
//		error.setError("AWS exception");
//		error.setMessage(e.getMessage());
//		error.setPath(request.getRequestURI());
//		
//		return ResponseEntity.status(status).body(error);
//	}
//
//	@ExceptionHandler(AmazonClientException.class)
//	public ResponseEntity<StandardError> amazonClient(AmazonClientException e, HttpServletRequest request) {
//		
//		HttpStatus status = HttpStatus.BAD_REQUEST;
//		StandardError error = new StandardError();
//		
//		error.setTimestamp(Instant.now());
//		error.setStatus(status.value());
//		error.setError("AWS exception");
//		error.setMessage(e.getMessage());
//		error.setPath(request.getRequestURI());
//		
//		return ResponseEntity.status(status).body(error);
//	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<StandardError> illegalArgument(IllegalArgumentException e, HttpServletRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError();
		
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Bad request");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
	}
	
}

