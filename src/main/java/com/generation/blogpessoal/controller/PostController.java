package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Post;
import com.generation.blogpessoal.repository.CategoryRepository;
import com.generation.blogpessoal.repository.PostRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostController {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping
	public ResponseEntity<List<Post>> getall(){
		return ResponseEntity.ok(postRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Post> getById(@PathVariable Long id){
		return postRepository.findById(id)
				.map(response -> ResponseEntity.ok(response))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/title/{title}")
	public ResponseEntity<List<Post>> getByTitle(@PathVariable String title){
		return ResponseEntity.ok(postRepository.findAllByTitleContainingIgnoreCase(title));
	}
	
	@PostMapping
	public ResponseEntity<Post> post(@Valid @RequestBody Post post){
		if(categoryRepository.existsById(post.getCategory().getId()))
			return ResponseEntity.status(HttpStatus.CREATED)
				.body(postRepository.save(post));
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe!", null);
	}
	
	@PutMapping
	public ResponseEntity<Post> put(@Valid @RequestBody Post post) {
		if(postRepository.existsById(post.getId())) {

		if(categoryRepository.existsById(post.getCategory().getId()))
			return ResponseEntity.status(HttpStatus.OK)
					.body(postRepository.save(post));
				
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Post> post = postRepository.findById(id);
		
		if(post.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

			postRepository.deleteById(id);
	}
	
	

	
}