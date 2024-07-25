package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Post;
import com.generation.blogpessoal.repository.PostRepository;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostController {
	
	@Autowired
	private PostRepository postRepository;
	
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
}