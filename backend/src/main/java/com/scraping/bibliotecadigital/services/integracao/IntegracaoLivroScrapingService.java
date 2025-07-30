package com.scraping.bibliotecadigital.services.integracao;

import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.scraping.bibliotecadigital.services.AutorService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource.Builder;

@Service
public class IntegracaoLivroScrapingService {
	
	private static Logger logger = LoggerFactory.getLogger(AutorService.class);

	public Map<String, String> extrairDadosLivroScrapingAmazonBooks(String url) throws Exception {
		
		try {
			return connectarLivroScrapingAmazonBooks(url);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage());
			throw e;
		}
	}
	
	private Map<String, String> connectarLivroScrapingAmazonBooks(String url) throws Exception {
		
		try {
			Client client = Client.create();
			
			Builder configured = client.resource(url)
					.header(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36")
					.header(HttpHeaders.ACCEPT_CHARSET, "UTF-8").header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML);
			
			ClientResponse response = configured.get(ClientResponse.class);
			String responseData = response.getEntity(String.class);
			
			Map<String, String> retorno = readAmazonBooks(responseData);

			if (response.getStatus() != 200) {
				
				throw new Exception("Erro ao enviar requisição para: " + url + ", status: " + response.getStatus());
			}

			return retorno;
			
		} catch (Exception e) {
			
			logger.error(e.getMessage());
			throw e;
		}
	}
	
	private Map<String, String> readAmazonBooks(String entrada) throws Exception {
		
		String linha = "";
		Map<String, String> saida = new TreeMap<>();
		
		try {
			if (entrada.contains("<span id=\"productTitle\" class=\"a-size-large celwidget\">")) {
				
				linha = entrada.substring(entrada.indexOf("<span id=\"productTitle\" class=\"a-size-large celwidget\">"));
				linha = linha.substring(linha.indexOf(">") + 1, linha.indexOf("</span>")).trim();
				saida.put("titulo", linha);
			}

			if (entrada.contains("ISBN-10")) {
				
				linha = entrada.substring(entrada.indexOf("ISBN-10"));
				linha = linha.substring(linha.indexOf("<div class=\"a-section a-spacing-none a-text-center rpi-attribute-value\"> <span>") + 1, linha.indexOf("</span> </div> </div>  </li>")).trim();
				linha = linha.substring(linha.indexOf("<span>") + 6).trim();
				saida.put("isbn", linha);
			}
			
			if (entrada.contains("<span id=\"productSubtitle\" class=\"a-size-medium a-color-secondary celwidget\">")) {
				
				linha = entrada.substring(entrada.indexOf("<span id=\"productSubtitle\" class=\"a-size-medium a-color-secondary celwidget\">"));
				linha = linha.substring(linha.indexOf(">") + 1, linha.indexOf("</span>")).trim();
				linha = linha.substring(linha.length() - 5, linha.length()).trim();
				saida.put("anoPublicacao", linha);
				
			}

			if (entrada.contains("class=\"a-price-whole\">")) {
				
				linha = entrada.substring(entrada.indexOf("class=\"a-price-whole\">"));
				linha = linha.substring(linha.indexOf(">") + 1, linha.indexOf("</span>")).trim();
				linha = linha.substring(0, linha.indexOf("<span class=\"a-price-decimal\">,")).trim();
				saida.put("preco", linha);

				if (entrada.contains("\"a-price-fraction\">")) {

					linha = entrada.substring(entrada.indexOf("\"a-price-fraction\">")).trim();
					linha = linha.substring(linha.indexOf("\"a-price-fraction\">"), linha.indexOf("</span></span></span>")).trim();
					linha = linha.substring(linha.indexOf(">") + 1).trim();
					saida.put("preco", saida.get("preco") + "." + linha);
				}
			}
			
		} catch (Exception e) {
			
			logger.error(e.getMessage());
			throw new Exception("Erro de acesso ao serviço : " + entrada);
		}
		
		return saida;
	}
	
}
