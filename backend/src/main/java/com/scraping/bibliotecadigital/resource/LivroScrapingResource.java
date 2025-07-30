package com.scraping.bibliotecadigital.resource;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.scraping.bibliotecadigital.dto.AutorDTO;
import com.scraping.bibliotecadigital.dto.CategoriaDTO;
import com.scraping.bibliotecadigital.dto.LivroDTO;
import com.scraping.bibliotecadigital.dto.LivroScrapingDTO;
import com.scraping.bibliotecadigital.services.LivroScrapingService;
import com.scraping.bibliotecadigital.services.LivroService;

import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/api/livros")
public class LivroScrapingResource {
	
	@Autowired
	private LivroService service;
	
	@Autowired
	private LivroScrapingService livroScraping;
	
	@PostMapping(value = "/importar")
	public ResponseEntity<LivroDTO> extrairDadosLivro(@Valid @RequestBody LivroScrapingDTO dto) {
		
		LivroDTO livroDTO = livroScraping.extrairDadosLivro(dto.getUrl());

		livroDTO.setAutorDTO(new AutorDTO(dto.getAutorId()));
		livroDTO.setCategoriaDTO(new CategoriaDTO(dto.getCategoriaId()));
		
		livroDTO = service.insert(livroDTO);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(livroDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(livroDTO);
	}
	
}
