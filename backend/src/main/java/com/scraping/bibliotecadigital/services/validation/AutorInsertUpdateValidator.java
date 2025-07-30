package com.scraping.bibliotecadigital.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.scraping.bibliotecadigital.dto.AutorDTO;
import com.scraping.bibliotecadigital.entities.Autor;
import com.scraping.bibliotecadigital.repositories.AutorRepository;
import com.scraping.bibliotecadigital.resource.exceptions.FieldMessage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AutorInsertUpdateValidator implements ConstraintValidator<AutorInsertUpdateValid, AutorDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private AutorRepository repository;
	
	@Override
	public void initialize(AutorInsertUpdateValid ann) {
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean isValid(AutorDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		Autor entity = repository.findByEmail(dto.getEmail());
		
		if (Objects.nonNull(entity)) {
			
			list.add(new FieldMessage("email", "Email já existe"));
			
			var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			
			if (Objects.nonNull(uriVars) && !uriVars.isEmpty()) {
				
				list.clear();
				Long autorId = Long.parseLong(uriVars.get("id"));
				
				if (Objects.nonNull(entity) && Objects.nonNull(autorId) && autorId != entity.getId()) {
					
					list.add(new FieldMessage("email", "Email já existe"));
				}
			}
		} 
			
		for (FieldMessage e : list) {
		
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		
		return list.isEmpty();
	}
	
}