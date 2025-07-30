package com.scraping.bibliotecadigital.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scraping.bibliotecadigital.dto.LivroDTO;
import com.scraping.bibliotecadigital.entities.Autor;
import com.scraping.bibliotecadigital.entities.Categoria;
import com.scraping.bibliotecadigital.entities.Livro;
import com.scraping.bibliotecadigital.repositories.AutorRepository;
import com.scraping.bibliotecadigital.repositories.CategoriaRepository;
import com.scraping.bibliotecadigital.repositories.LivroRepository;
import com.scraping.bibliotecadigital.services.exceptions.DataBaseException;
import com.scraping.bibliotecadigital.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LivroService {
	
	private static Logger logger = LoggerFactory.getLogger(LivroService.class);

	@Autowired
	private LivroRepository repository;

	@Autowired
	private AutorRepository autorRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Transactional(readOnly = true)
	public List<LivroDTO> findAll(Long categoria, Integer ano, Long autor) {
		
		List<Livro> list = repository.findAllLivroWithCategoriaAnoAutor(categoria, ano, autor);
		
		return list.stream().map(x -> new LivroDTO(x)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<LivroDTO> findByTitulo(String titulo) {
		
		List<Livro> list = repository.findByTitulo(titulo);
		
		return list.stream().map(x -> new LivroDTO(x)).collect(Collectors.toList());
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
	
	@Transactional
	private void copyDtoToEntity(LivroDTO dto, Livro entity) {
		
		entity.setTitulo(dto.getTitulo());
		entity.setIsbn(dto.getIsbn());
		entity.setAnoPublicacao(dto.getAnoPublicacao());
		entity.setPreco(dto.getPreco());
		
		Autor autor = new Autor();
		Categoria categoria = new Categoria();
		
		if (Objects.nonNull(dto.getAutorDTO().getId())) {
			
			Optional<Autor> obj = autorRepository.findById(dto.getAutorDTO().getId());
			
			if (!obj.isEmpty() && Objects.nonNull(obj.get())) {
				
				autor = obj.get();
				
				if (Objects.isNull(dto.getAutorDTO().getNome())) {
					
					dto.getAutorDTO().setNome(autor.getNome());
				}

				if (Objects.isNull(dto.getAutorDTO().getEmail())) {
					
					dto.getAutorDTO().setEmail(autor.getEmail());
				}

				if (Objects.isNull(dto.getAutorDTO().getDataNascimento())) {
					
					dto.getAutorDTO().setDataNascimento(autor.getDataNascimento());
				}
			}
		}
		
		if (Objects.nonNull(dto.getCategoriaDTO().getId())) {
			
			Optional<Categoria> obj = categoriaRepository.findById(dto.getCategoriaDTO().getId());
			
			if (!obj.isEmpty() && Objects.nonNull(obj.get())) {
				
				categoria = obj.get();
				
				if (Objects.isNull(dto.getCategoriaDTO().getNome())) {
					
					dto.getCategoriaDTO().setNome(categoria.getNome());
				}
				
				if (Objects.isNull(dto.getCategoriaDTO().getDescricao())) {
					
					dto.getCategoriaDTO().setDescricao(categoria.getDescricao());
				}
			}
		}
		
		autor.setNome(dto.getAutorDTO().getNome());
		autor.setEmail(dto.getAutorDTO().getEmail());
		autor.setDataNascimento(dto.getAutorDTO().getDataNascimento());
		
		categoria.setNome(dto.getCategoriaDTO().getNome());
		categoria.setDescricao(dto.getCategoriaDTO().getDescricao());
		
		entity.setAutor(autor);
		entity.setCategoria(categoria);
	}

}
