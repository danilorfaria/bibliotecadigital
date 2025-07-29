package com.scraping.bibliotecadigital.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scraping.bibliotecadigital.dto.LivroDTO;
import com.scraping.bibliotecadigital.entities.Livro;
import com.scraping.bibliotecadigital.services.integracao.IntegracaoLivroScrapingService;

@Service
public class LivroScrapingService {
	
	private static Logger logger = LoggerFactory.getLogger(LivroScrapingService.class);

	@Autowired
	private IntegracaoLivroScrapingService integracaoLivroScrapingService;
	
	@Transactional
	public LivroDTO extrairDadosLivro(String url) {
		
		integracaoLivroScrapingService.extrairDadosLivroScrapingAmazonBooks(url);
		
		Livro entity = new Livro();
		
		return new LivroDTO(entity);
	}

}
