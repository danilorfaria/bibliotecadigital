package com.scraping.bibliotecadigital.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scraping.bibliotecadigital.dto.AutorDTO;
import com.scraping.bibliotecadigital.entities.Autor;
import com.scraping.bibliotecadigital.repositories.AutorRepository;
import com.scraping.bibliotecadigital.services.exceptions.DataBaseException;
import com.scraping.bibliotecadigital.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AutorService {
	
	@Autowired
	private AutorRepository repository;
	
	@Transactional(readOnly = true)
	public List<AutorDTO> findAll() {
		
		List<Autor> list = repository.findAll();
		
		return list.stream().map(x -> new AutorDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public Page<AutorDTO> findAllPaged(PageRequest pageRequest) {

		Page<Autor> list = repository.findAll(pageRequest);
		
		return list.map(x -> new AutorDTO(x));
	}

	@Transactional(readOnly = true)
	public AutorDTO findById(Long id) {
		
		Optional<Autor> obj = repository.findById(id);
		
		Autor entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		
		return new AutorDTO(entity);
	}

	@Transactional
	public AutorDTO insert(AutorDTO dto) {
		
		Autor entity = new Autor();
		
		copyDtoToEntity(dto, entity);
		
		entity = repository.save(entity);
		
		return new AutorDTO(entity);
	}

	@Transactional
	public AutorDTO update(Long id, AutorDTO dto) {
		
		try {
			Autor entity = repository.getReferenceById(id);
			
			copyDtoToEntity(dto, entity);
			
			entity = repository.save(entity);
			
			return new AutorDTO(entity);
			
		} catch (EntityNotFoundException e) {
			
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	public void delete(Long id) {
		
		try {
			repository.deleteById(id);
			
		} catch (EmptyResultDataAccessException e) {
		
			throw new ResourceNotFoundException("Id not found " + id);
			
		} catch (DataIntegrityViolationException e) {
			
			throw new DataBaseException("Integrity violation");
		}
	}
	
	private void copyDtoToEntity(AutorDTO dto, Autor entity) {
		
		entity.setNome(dto.getNome());
		entity.setEmail(dto.getEmail());
		entity.setDataNascimento(dto.getDataNascimento());
	}

}
