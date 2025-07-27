package com.scraping.bibliotecadigital.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.scraping.bibliotecadigital.dto.AutorDTO;
import com.scraping.bibliotecadigital.dto.LivroDTO;
import com.scraping.bibliotecadigital.services.AutorService;

@RestController
@RequestMapping(value = "/api/autores")
public class AutorResource {
	
	@Autowired
	private AutorService service;
	
	@GetMapping
	public ResponseEntity<Page<AutorDTO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage);
		
		Page<AutorDTO> list = service.findAllPaged(pageRequest);
		
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<AutorDTO> findById(@PathVariable Long id) {
		
		AutorDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<AutorDTO> insert(@RequestBody AutorDTO dto) {
		
		dto = service.insert(dto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<AutorDTO> update(@PathVariable Long id, @RequestBody AutorDTO dto) {
		
		dto = service.update(id, dto);
		
		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/{id}/livros")
	public ResponseEntity<List<LivroDTO>> listarLivrosAutor(@PathVariable Long id) {
		
		List<LivroDTO> list = service.listarLivrosAutor(id);
		return ResponseEntity.ok().body(list);
	}
	
}
