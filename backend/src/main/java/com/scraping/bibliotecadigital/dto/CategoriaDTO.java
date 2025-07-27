package com.scraping.bibliotecadigital.dto;

import java.io.Serializable;

import com.scraping.bibliotecadigital.entities.Categoria;

public class CategoriaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String nome;

	private String descricao;
	
	public CategoriaDTO() {
		super();
	}
	
	public CategoriaDTO(Long id, String nome, String descricao) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
	}
	
	public CategoriaDTO(Categoria category) {
		super();
		this.id = category.getId();
		this.nome = category.getNome();
		this.descricao = category.getDescricao();
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
