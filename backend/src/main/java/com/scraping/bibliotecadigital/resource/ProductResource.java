package com.devsuperior.dscatalog.resource;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dscatalog.Services.ProductService;
import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.dto.UriDTO;


@RestController
@RequestMapping(value = "/products")
public class ProductResource {
	
	@Autowired
	private LivroService service;
	
	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAll(
			@RequestParam(value = "categoryId", defaultValue = "0") Long categoryId,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy
			) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Page<ProductDTO> list = service.findAllPaged(categoryId, name.trim(), pageRequest);

		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		
		ProductDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO dto) {
		
		dto = service.insert(dto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@PostMapping(value = "/image")
	public ResponseEntity<UriDTO> uploadImage(@RequestParam("file") MultipartFile file) {
		
		UriDTO dto = service.uploadFile(file);
		
		return ResponseEntity.ok().body(dto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
		
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	public static void main(String[] args) {
		
//		boolean retorno = false;
		
		
//		int[] nums = {1, 1, 2, 3, 1};
//		int[] nums = {1, 1, 2, 4, 1};
//		int[] nums = {1, 1, 2, 1, 2, 3};
//		int[] nums = {1, 1, 2, 1, 2, 1};
//		int[] nums = {1, 2, 3, 1, 2, 3};
//		int[] nums = {1, 2, 3};
//		int[] nums = {1, 1, 1};
//		int[] nums = {1, 2};
//		int[] nums = {1};
		int[] nums = {};

	    
	    List<Integer> lista = new ArrayList<Integer>();
	    
	    for(int i = 0; i < nums.length; i++) {
	      
	        lista.add(nums[i]) ;
	        
	    }
		
		List<Integer> pares = lista.stream().filter(num -> (num == 1 || num == 2 || num == 3)).collect(Collectors.toList());
		
		System.out.println(pares);
		
		System.out.println(pares.contains(1));
		System.out.println(pares.contains(2));
		System.out.println(pares.contains(3));
		
//		ProductResource pr = new ProductResource();
//
//		for (Integer item : pares) {
//			
//			System.out.println(pr.seguencia123(item));
//		}
//		
	    
//	    List<Integer> pares = lista.stream().filter(num -> (num == 1 || num == 2 || num == 3)).collect(Collectors.toList());
	    //Stream<Integer> pares = lista.stream().filter(num -> num == 1 && num == 2 && num == 3);
	    
//	    System.out.println(pares);
	    
//	    retorno = pares.stream().filter(num -> num == 1 && num == 2 && num == 3).isParallel();
	    
//	    System.out.println(retorno);
		
//		ProductResource pr = new ProductResource();
//		
//		pr.array123(nums);
	    
	}
	
	public boolean array123(int[] nums) {
		  
		  boolean retorno = false;
		  
		  if (nums == null || nums.length < 3) {
		   
		    return false;
		  }
		  
		  if (nums != null && nums.length > 0) {
		    
		    for (int index : nums) {
		    	
		    	
		    	 if (index == 1) {
		 	        
		 	        retorno = true;
		 	      } else 
		 	      
		 	      if (index == 2) {
		 	        
		 	        retorno = true;
		 	      } else 
		 	      
		 	       if (index == 3) {
		 	        
		 	        retorno = true;
		 	      } 
		      
		    	//System.out.println(seguencia123(index));
		    	//System.out.println(seguenciaDiferente123(index));
		 	     System.out.println(retorno);
		    }
		    
		    return retorno;
		  } 
		  
		  return retorno;
		}
	
	private boolean seguencia123(int index) {
		  
	      boolean retorno = false;
	  
	      if (index == 1 && index != 2 && index != 3) {
	        
	        retorno = true;
	      } 
	      
	      if (index != 1 && index == 2 && index != 3) {
	        
	        retorno = true;
	      } 
	      
	       if (index != 1 && index != 2 && index == 3) {
	        
	        retorno = true;
	      } 
	  
	  
	    return retorno;
	}
	
	private boolean seguenciaDiferente123(int index) {
		  
	      boolean retorno = false;
	  
	      if ((index == 1 || index == 2) && index != 3) {
	        
	        retorno = true;
	      } else 
	      
	      if (index != 1 && (index == 2 || index == 3)) {
	        
	         retorno = true;
	      } else 
	      
	       if ((index == 1 || index == 3) && index != 2) {
	        
	        retorno = true;
	      } 
	  
	  
	    return retorno;
	}
	
	
}
