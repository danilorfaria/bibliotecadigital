package com.scraping.bibliotecadigital.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.scraping.bibliotecadigital.entities.Autor;

public class AutorDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String nome;

	private String email;
	
	private LocalDate dataNascimento;
	
	public AutorDTO() {
		super();
	}

	public AutorDTO(Long id, String nome, String email, LocalDate dataNascimento) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.dataNascimento = dataNascimento;
	}
	
	public AutorDTO(Autor autor) {
		super();
		this.id = autor.getId();
		this.nome = autor.getNome();
		this.email = autor.getEmail();
		this.dataNascimento = autor.getDataNascimento();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

}
