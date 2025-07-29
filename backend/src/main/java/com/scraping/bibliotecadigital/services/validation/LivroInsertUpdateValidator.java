package com.scraping.bibliotecadigital.services.validation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import com.scraping.bibliotecadigital.dto.LivroDTO;
import com.scraping.bibliotecadigital.resource.exceptions.FieldMessage;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LivroInsertUpdateValidator implements ConstraintValidator<LivroInsertUpdateValid, LivroDTO> {
	
	@Override
	public void initialize(LivroInsertUpdateValid ann) {
	}

	@Override
	public boolean isValid(LivroDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
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