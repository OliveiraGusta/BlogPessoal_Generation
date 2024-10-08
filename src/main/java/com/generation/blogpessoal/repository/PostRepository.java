package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import com.generation.blogpessoal.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	public List<Post> findAllByTitleContainingIgnoreCase(@Param("title") String title);
	
}
