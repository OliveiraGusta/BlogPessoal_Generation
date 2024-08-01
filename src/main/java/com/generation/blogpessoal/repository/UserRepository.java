package com.generation.blogpessoal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.blogpessoal.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public Optional<User> findByEmail(String email);
}
