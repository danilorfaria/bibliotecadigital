package com.scraping.bibliotecadigital.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
		
		Autor autor = autorRepository.getReferenceById(dto.getAutorDTO().getId());
		Categoria categoria = categoriaRepository.getReferenceById(dto.getCategoriaDTO().getId());
		
		autor.setNome(dto.getAutorDTO().getNome());
		autor.setEmail(dto.getAutorDTO().getEmail());
		autor.setDataNascimento(dto.getAutorDTO().getDataNascimento());
		
		categoria.setNome(dto.getCategoriaDTO().getNome());
		categoria.setDescricao(dto.getCategoriaDTO().getDescricao());
		
		entity.setAutor(autor);
		entity.setCategoria(categoria);
	}
	
//	public UriDTO uploadFile(MultipartFile file) {
//		
//		URL url = s3Service.uploadFile(file);
//		
//		return new UriDTO(url.toString());
//	}

}
