package com.scraping.bibliotecadigital.services;

import java.math.BigDecimal;
import java.util.Arrays;
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

import com.scraping.bibliotecadigital.dto.CategoriaDTO;
import com.scraping.bibliotecadigital.dto.LivroDTO;
import com.scraping.bibliotecadigital.entities.Autor;
import com.scraping.bibliotecadigital.entities.Categoria;
import com.scraping.bibliotecadigital.entities.Livro;
import com.scraping.bibliotecadigital.repositories.CategoriaRepository;
import com.scraping.bibliotecadigital.repositories.LivroRepository;
import com.scraping.bibliotecadigital.services.exceptions.DataBaseException;
import com.scraping.bibliotecadigital.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Service
public class LivroService {

	@Autowired
	private LivroRepository repository;
	
//	@Autowired
//	private CategoriaRepository categoriaRepository;
	
	@Transactional(readOnly = true)
	public List<LivroDTO> findAll() {
		
		List<Livro> list = repository.findAll();
		return list.stream().map(x -> new LivroDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public Page<LivroDTO> findAllPaged(Long categoriaId, String name, PageRequest pageRequest) {
		
//		Page<Livro> page = repository.find(categoria, name, pageRequest);
//		
//		repository.findProductsWithCategories(page.getContent());
//		
//		return page.map(x -> new LivroDTO(x, x.getCategoria));
		
		return null;
	}

	@Transactional(readOnly = true)
	public LivroDTO findById(Long id) {
		
		Optional<Livro> obj = repository.findById(id);
		Livro entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		
		return new LivroDTO(entity);
	}

	@Transactional
	public LivroDTO insert(LivroDTO dto) {
		
		Livro entity = new Livro();
		
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		
		return new LivroDTO(entity);
	}

	@Transactional
	public LivroDTO update(Long id, LivroDTO dto) {
		
		try {
			Livro entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			
			entity = repository.save(entity);
			
			return new LivroDTO(entity);
			
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
	
	private void copyDtoToEntity(LivroDTO dto, Livro entity) {
		
		entity.setTitulo(dto.getTitulo());
		entity.setIsbn(dto.getIsbn());
		entity.setAnoPublicacao(dto.getAnoPublicacao());
		entity.setPreco(dto.getPreco());
		entity.setAutor(dto.getAutorDTO());
		entity.setCategoria(dto.getCategoriaDTO());
		
//		entity.getCategories().clear();
//		
//		for (CategoriaDTO catDto : dto.getCategoriaDTO()) {
//			
//			Categoria categoria = categoriaRepository.getOne(catDto.getId());
//			entity.getCategoria().add(categoria);
//		}
	}

//	public UriDTO uploadFile(MultipartFile file) {
//		
//		URL url = s3Service.uploadFile(file);
//		
//		return new UriDTO(url.toString());
//	}

}
