package com.scraping.bibliotecadigital.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.scraping.bibliotecadigital.entities.Livro;
import com.scraping.bibliotecadigital.services.validation.LivroInsertUpdateValid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@LivroInsertUpdateValid
public class LivroDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotBlank(message = "Título do livro não pode ser vazio")
	private String titulo;

	@Pattern(regexp = "\\d{10}|\\d{13}", message = "ISBN deve ter formato válido (10 ou 13 dígitos)")
	private String isbn;
	
	private Integer anoPublicacao;
	
	@Positive(message = "Preço deve ser positivo")
	private BigDecimal preco;
	
	private AutorDTO autorDTO;
	
	private CategoriaDTO categoriaDTO;
	
	public LivroDTO() {
		
	}

	public LivroDTO(Long id, String titulo, String isbn, Integer anoPublicacao, BigDecimal preco, AutorDTO autorDTO,
			CategoriaDTO categoriaDTO) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.isbn = isbn;
		this.anoPublicacao = anoPublicacao;
		this.preco = preco;
		this.autorDTO = autorDTO;
		this.categoriaDTO = categoriaDTO;
	}
	
	public LivroDTO(Livro entity) {
		super();
		this.id = entity.getId();
		this.titulo = entity.getTitulo();
		this.isbn = entity.getIsbn();
		this.anoPublicacao = entity.getAnoPublicacao();
		this.preco = entity.getPreco();
		this.autorDTO = new AutorDTO(entity.getAutor());
		this.categoriaDTO = new CategoriaDTO(entity.getCategoria());
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getAnoPublicacao() {
		return anoPublicacao;
	}

	public void setAnoPublicacao(Integer anoPublicacao) {
		this.anoPublicacao = anoPublicacao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public AutorDTO getAutorDTO() {
		return autorDTO;
	}

	public void setAutorDTO(AutorDTO autorDTO) {
		this.autorDTO = autorDTO;
	}

	public CategoriaDTO getCategoriaDTO() {
		return categoriaDTO;
	}

	public void setCategoriaDTO(CategoriaDTO categoriaDTO) {
		this.categoriaDTO = categoriaDTO;
	}

}
