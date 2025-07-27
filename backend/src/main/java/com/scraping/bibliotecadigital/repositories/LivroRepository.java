package com.scraping.bibliotecadigital.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scraping.bibliotecadigital.entities.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
	
	@Query("SELECT obj FROM Livro obj WHERE obj.autor.id = :autor ")
	List<Livro> findLivroWithAutor(Long autor);

	@Query("SELECT obj FROM Livro obj WHERE obj.categoria.id = :categoria ")
	List<Livro> findLivroWithCategoria(Long categoria);

	@Query("SELECT obj FROM Livro obj WHERE obj.categoria.id = :categoria AND obj obj.anoPublicacao = :ano AND obj.autor.id = :autor")
	List<Livro> findAllLivroWithCategoriaAnoAutor(Long categoria, Integer ano, Long autor);
	
	@Query("SELECT obj FROM Livro obj WHERE "
		+ "(LOWER(obj.titulo) LIKE LOWER(CONCAT ('%', :titulo, '%')) ) ")
	 List<Livro> findByTitulo(String titulo);
	
	

}
