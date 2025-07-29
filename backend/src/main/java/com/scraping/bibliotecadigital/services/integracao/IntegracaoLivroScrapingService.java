package com.scraping.bibliotecadigital.services.integracao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

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

	public Map<String, String> extrairDadosLivroScrapingAmazonBooks(String url) {
		
		try {
			return connectarLivroScrapingAmazonBooks(url);
			
		} catch (Exception e) {
			
			logger.info(e.getMessage());
			return null; 
		}
	}
	
	private Map<String, String> connectarLivroScrapingAmazonBooks(String url) {
		
		try {
			Client client = Client.create();
			
			Builder configured = client.resource(url).type(MediaType.TEXT_HTML);
			
			ClientResponse response = configured.get(ClientResponse.class);
//			String responseData = response.getEntity(String.class);
			StringBuilder responseData = new StringBuilder();

			String linha = "";;
			InputStream inputStream = response.getEntityInputStream();
					
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
				
				while (Objects.nonNull((linha = reader.readLine()))) {
					
					responseData.append(linha); 
				}
				
			} finally {
				
				inputStream.close();
			}
			
			Map<String, String> retorno = readAmazonBooks(responseData.toString());

			if (response.getStatus() != 200) {
				
				
				throw new Exception("Erro ao enviar requisição para o SIICO, status " + response.getStatus());
			}

			return retorno;
			
		} catch (Exception e) {
			
			logger.info(e.getMessage());
			return null;
		}
	}
	
	private Map<String, String> readAmazonBooks(String entrada) throws Exception {
		
		Map<String, String> saida = new TreeMap<>();
		String linha = "";
		
		try {
//			entrada.startsWith("<span id=\"productTitle\" class=\"a-size-large celwidget\">")
			linha = entrada.substring(entrada.indexOf("<span id=\"productTitle\" class=\"a-size-large celwidget\">"), entrada.indexOf("</span>"));
			System.out.println(linha);
			
			System.out.println(entrada.replaceAll("<span id=\"productTitle\" class=\"a-size-large celwidget\">", "").replaceAll("</span>", "").trim());
			
			System.out.println(entrada.replaceAll("ISBN-10 &rlm; :&lrm;</span> <span>", "").replaceAll("</span> </span></li>", "").trim());

			System.out.println(entrada.replaceAll("<span id=\"productSubtitle\" class=\"a-size-medium a-color-secondary celwidget\">", "").replace("</span>", "").trim());
			
			System.out.println(entrada.replaceAll("class=\"a-price-whole\">", "").replaceAll("<span", "").trim() + "." + entrada.replaceAll("\"a-price-fraction\">", "").replaceAll("</span>", "").trim());

			
			saida.put("titulo", entrada.replace("<span id=\"productTitle\" class=\"a-size-large celwidget\">", "").replace("</span>", "").trim());
			saida.put("isbn", entrada.replace("ISBN-10 &rlm; :&lrm;</span> <span>", "").replace("</span> </span></li>", "").trim());
			saida.put("anoPublicacao", entrada.replace("<span id=\"productSubtitle\" class=\"a-size-medium a-color-secondary celwidget\">", "").replace("</span>", "").trim());
			saida.put("preco", entrada.replace("class=\"a-price-whole\">", "").replace("<span", "").trim() + "." + entrada.replace("\"a-price-fraction\">", "").replace("</span>", "").trim());
			
			
		} catch (Exception e) {
			
			logger.info(e.getMessage());
			throw new Exception("Erro de acesso ao serviço : " + entrada);
		}
		
		return saida;
	}
	
}
