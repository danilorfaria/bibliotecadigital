package com.scraping.bibliotecadigital.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.scraping.bibliotecadigital.dto.LivroDTO;
import com.scraping.bibliotecadigital.services.LivroService;

import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/api/livros")
public class LivroResource {
	
	@Autowired
	private LivroService service;
	
	@GetMapping
	public ResponseEntity<List<LivroDTO>> findAll(
			@RequestParam(value = "categoria", required = false) Long categoria,
			@RequestParam(value = "ano", required = false) Integer ano,
			@RequestParam(value = "autor", required = false) Long autor) {

		List<LivroDTO> list = service.findAll(categoria, ano, autor);
		
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<LivroDTO> findById(@PathVariable Long id) {
		
		LivroDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<LivroDTO> insert(@Valid @RequestBody LivroDTO dto) {
		
		dto = service.insert(dto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<LivroDTO> update(@PathVariable Long id, @Valid @RequestBody LivroDTO dto) {
		
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/search")
	public  ResponseEntity<List<LivroDTO>> findByTitulo(@RequestParam String titulo) {
		
		List<LivroDTO> list = service.findByTitulo(titulo);
		
		return ResponseEntity.ok().body(list);
	}

//	@PostMapping(value = "/image")
//	public ResponseEntity<UriDTO> uploadImage(@RequestParam("file") MultipartFile file) {
//		
//		UriDTO dto = service.uploadFile(file);
//		
//		return ResponseEntity.ok().body(dto);
//	}
	
}
