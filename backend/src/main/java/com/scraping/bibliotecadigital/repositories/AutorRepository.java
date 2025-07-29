package com.scraping.bibliotecadigital.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scraping.bibliotecadigital.entities.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

	Autor findByEmail(String email);
}
