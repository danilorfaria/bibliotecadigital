package com.scraping.bibliotecadigital.services.validation;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.scraping.bibliotecadigital.dto.LivroScrapingDTO;
import com.scraping.bibliotecadigital.resource.exceptions.FieldMessage;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class URLValidator implements ConstraintValidator<URLValid, LivroScrapingDTO> {
	
	@Override
	public void initialize(URLValid ann) {
	}

	@Override
	public boolean isValid(LivroScrapingDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		try {
			
			new URL(dto.getUrl()).toURI(); 
		
		}catch (MalformedURLException | URISyntaxException | IllegalArgumentException e) {
			
            list.add(new FieldMessage("url", "URL inválida"));
        }
		
			
		for (FieldMessage e : list) {
		
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		
		return list.isEmpty();
	}
	
}