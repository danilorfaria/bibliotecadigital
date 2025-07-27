package com.scraping.bibliotecadigital.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scraping.bibliotecadigital.entities.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
	
//	@Query("SELECT DISTINCT obj FROM Product obj INNER JOIN obj.categories cast WHERE "
//			+ "(COALESCE(:categories) IS NULL OR cast IN :categories ) AND"
//			+ "(LOWER(obj.name) LIKE LOWER(CONCAT ('%', :name, '%')) ) ")
//	Page<Product> find(List<Category> categories, String name, Pageable pageable);
//
	
	@Query("SELECT obj FROM Livro obj INNER JOIN obj.autor WHERE obj.autor.id = :autor ")
	List<Livro> findLivroWithAutor(Long autor);

}
