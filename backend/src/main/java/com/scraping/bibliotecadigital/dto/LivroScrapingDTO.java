package com.scraping.bibliotecadigital.dto;

import java.io.Serializable;

import com.scraping.bibliotecadigital.services.validation.URLValid;

@URLValid
public class LivroScrapingDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String url;

	private Long autorId;

	private Long categoriaId;
	
	public LivroScrapingDTO() {
		super();
	}

	public LivroScrapingDTO(String url, Long autorId, Long categoriaId) {
		super();
		this.url = url;
		this.autorId = autorId;
		this.categoriaId = categoriaId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getAutorId() {
		return autorId;
	}

	public void setAutorId(Long autorId) {
		this.autorId = autorId;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

}
