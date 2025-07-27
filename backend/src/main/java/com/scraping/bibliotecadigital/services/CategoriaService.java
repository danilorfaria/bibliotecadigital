package com.scraping.bibliotecadigital.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scraping.bibliotecadigital.dto.CategoriaDTO;
import com.scraping.bibliotecadigital.dto.LivroDTO;
import com.scraping.bibliotecadigital.entities.Categoria;
import com.scraping.bibliotecadigital.entities.Livro;
import com.scraping.bibliotecadigital.repositories.CategoriaRepository;
import com.scraping.bibliotecadigital.repositories.LivroRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	
	@Autowired
	private LivroRepository livroRepository;
	
	@Transactional(readOnly = true)
	public List<CategoriaDTO> findAll() {
		
		List<Categoria> list = repository.findAll();
		
		return list.stream().map(x -> new CategoriaDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public CategoriaDTO insert(CategoriaDTO dto) {
		
		Categoria entity = new Categoria();
		
		copyDtoToEntity(dto, entity);
		
		entity = repository.save(entity);
		
		return new CategoriaDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public List<LivroDTO> listarLivrosCategoria(Long autorId) {
		
		List<Livro> list = livroRepository.findLivroWithCategoria(autorId);
		
		return list.stream().map(x -> new LivroDTO(x)).collect(Collectors.toList());
	}

	private void copyDtoToEntity(CategoriaDTO dto, Categoria entity) {
		
		entity.setNome(dto.getNome());
		entity.setDescricao(dto.getDescricao());
	}

}
