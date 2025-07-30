package com.scraping.bibliotecadigital.services.validation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.scraping.bibliotecadigital.dto.LivroDTO;
import com.scraping.bibliotecadigital.entities.Livro;
import com.scraping.bibliotecadigital.repositories.LivroRepository;
import com.scraping.bibliotecadigital.resource.exceptions.FieldMessage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LivroInsertUpdateValidator implements ConstraintValidator<LivroInsertUpdateValid, LivroDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private LivroRepository repository;
	
	@Override
	public void initialize(LivroInsertUpdateValid ann) {
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean isValid(LivroDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		Livro entity = repository.findByIsbn(dto.getIsbn());
		
		if (Objects.nonNull(entity)) {
			
			list.add(new FieldMessage("isbn", "Isbn já existe"));
			
			var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			
			if (Objects.nonNull(uriVars) && !uriVars.isEmpty()) {
				
				list.clear();
				Long livroId = Long.parseLong(uriVars.get("id"));
				
				if (Objects.nonNull(entity) && Objects.nonNull(livroId) && livroId != entity.getId()) {
					
					list.add(new FieldMessage("isbn", "Isbn já existe"));
				}
			}
		}
		
		if (Objects.nonNull(dto) && Objects.nonNull(dto.getAnoPublicacao()) ) {
				
			if (dto.getAnoPublicacao() > Calendar.getInstance().get(Calendar.YEAR)) {
				
				list.add(new FieldMessage("anoPublicacao", "Ano de publicação não pode ser futuro"));
			}
		} 
			
		for (FieldMessage e : list) {
		
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		
		return list.isEmpty();
	}
	
}