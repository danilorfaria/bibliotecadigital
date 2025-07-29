package com.scraping.bibliotecadigital.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scraping.bibliotecadigital.dto.AutorDTO;
import com.scraping.bibliotecadigital.dto.LivroDTO;
import com.scraping.bibliotecadigital.entities.Autor;
import com.scraping.bibliotecadigital.entities.Livro;
import com.scraping.bibliotecadigital.repositories.AutorRepository;
import com.scraping.bibliotecadigital.repositories.LivroRepository;
import com.scraping.bibliotecadigital.services.exceptions.DataBaseException;
import com.scraping.bibliotecadigital.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AutorService {
	
	private static Logger logger = LoggerFactory.getLogger(AutorService.class);
	
	@Autowired
	private AutorRepository repository;

	@Autowired
	private LivroRepository livroRepository;
	
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
	
	@Transactional(readOnly = true)
	public List<LivroDTO> listarLivrosAutor(Long autorId) {
		
		List<Livro> list = livroRepository.findLivroWithAutor(autorId);
		
		return list.stream().map(x -> new LivroDTO(x)).collect(Collectors.toList());
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
			
			logger.error(e.getMessage());
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	public void delete(Long id) {
		
		try {
			repository.deleteById(id);
			
		} catch (EmptyResultDataAccessException e) {
		
			logger.error(e.getMessage());
			throw new ResourceNotFoundException("Id not found " + id);
			
		} catch (DataIntegrityViolationException e) {
			
			logger.error(e.getMessage());
			throw new DataBaseException("Integrity violation");
		}
	}
	
	private void copyDtoToEntity(AutorDTO dto, Autor entity) {
		
		entity.setNome(dto.getNome());
		entity.setEmail(dto.getEmail());
		entity.setDataNascimento(dto.getDataNascimento());
	}

}
