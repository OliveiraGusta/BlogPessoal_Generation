package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.User;
import com.generation.blogpessoal.repository.UserRepository;
import com.generation.blogpessoal.service.UserService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@BeforeAll
	void start(){
		 
		userRepository.deleteAll();
		
		userService.registerUser(new User(0L, "Root", "root@root.com", "rootroot", "--"));
		
	}
	
	@Test
	@DisplayName("Cadastrar um usuario")
	public void testCreateNewUser() {
		HttpEntity<User> requestBody = new HttpEntity<User> (new  User(0L, "Gustavo", "gustavo@root.com", "root123", "--"));
		
		ResponseEntity<User> responseBody = testRestTemplate.exchange("/users/register" , HttpMethod.POST, requestBody, User.class);
		
		assertEquals(HttpStatus.CREATED, responseBody.getStatusCode());
	}
	
	@Test
	@DisplayName("Não deve permitir dupla de usuario")
	public void testNotDuplicateUser() {
		
		userService.registerUser(new User(0L, "thay", "thay@root.com", "thay123", "---"));
		
		HttpEntity<User> requestBody = new HttpEntity<User> (new  User(0L, "thay", "thay@root.com", "thay123", "---"));

		ResponseEntity<User> responseBody = testRestTemplate.exchange("/users/register" , HttpMethod.POST, requestBody, User.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseBody.getStatusCode());

	}
	
	@Test
	@DisplayName("Update um usuario")
	public void testUpdateUser() {
		
		Optional<User> userRegistered =	userService.registerUser(new User(0L, "thay2", "thay2@root.com", "thay123", "---"));
		
		User userUpdate = new User(userRegistered.get().getId(), "thay meu bb", "thay2@root.com", "thay123", "---");
		
		HttpEntity<User> requestBody = new HttpEntity<User>(userUpdate);
		
		ResponseEntity<User> responseBody = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/users/update", HttpMethod.PUT, requestBody, User.class );
		
		assertEquals(HttpStatus.OK, responseBody.getStatusCode());
	}
	
	@Test
	@DisplayName("Listar todos os usuarios")
	public void testShowAllUsers() {
		userService.registerUser(new User(0L, "thay3", "thay3@root.com", "thay123", "---"));
		
		userService.registerUser(new User(0L, "thay4", "thay4@root.com", "thay123", "---"));
	
		ResponseEntity<String> response = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/users/all", HttpMethod.GET, null, String.class);
		
		
		
	}
	
	
	
}







