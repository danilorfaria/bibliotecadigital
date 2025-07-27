package com.scraping.bibliotecadigital.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.scraping.bibliotecadigital.dto.CategoriaDTO;
import com.scraping.bibliotecadigital.dto.LivroDTO;
import com.scraping.bibliotecadigital.services.CategoriaService;

@RestController
@RequestMapping(value = "/api/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll() {

		List<CategoriaDTO> list = service.findAll();
		
		return ResponseEntity.ok().body(list);
	}

	@PostMapping
	public ResponseEntity<CategoriaDTO> insert(@RequestBody CategoriaDTO dto) {
		
		dto = service.insert(dto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}
	
	@GetMapping(value = "/{id}/livros")
	public ResponseEntity<List<LivroDTO>> listarLivrosAutor(@PathVariable Long id) {
		
		List<LivroDTO> list = service.listarLivrosCategoria(id);
		return ResponseEntity.ok().body(list);
	}
	
}
