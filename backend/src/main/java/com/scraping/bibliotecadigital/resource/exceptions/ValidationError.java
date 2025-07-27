package com.scraping.bibliotecadigital.resource.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>();

	public void addError(String fielName, String message) {
		
		errors.add(new FieldMessage(fielName, message));
	}
	
	public List<FieldMessage> getErrors() {
		return errors;
	}

}
