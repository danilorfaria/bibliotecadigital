package com.scraping.bibliotecadigital.services;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scraping.bibliotecadigital.dto.LivroDTO;
import com.scraping.bibliotecadigital.services.integracao.IntegracaoLivroScrapingService;

@Service
public class LivroScrapingService {
	
	private static Logger logger = LoggerFactory.getLogger(LivroScrapingService.class);

	@Autowired
	private IntegracaoLivroScrapingService integracaoLivroScrapingService;
	
	@Transactional
	public LivroDTO extrairDadosLivro(String url) {

		LivroDTO dto = new LivroDTO();
		
		try {
			Map<String, String> retorno = integracaoLivroScrapingService.extrairDadosLivroScrapingAmazonBooks(url);
			
			dto.setTitulo(retorno.get("titulo"));
			dto.setIsbn(retorno.get("isbn"));
			dto.setAnoPublicacao(Integer.valueOf(retorno.get("anoPublicacao")));
			dto.setPreco(new BigDecimal(retorno.get("preco")));
			
		} catch (Exception e) {

			logger.error(e.getMessage());
			return null;
		}
		
		return dto;
	}

}
