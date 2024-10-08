package com.generation.blogpessoal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_users")
public class User {
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O atributo NAME é Obrigatorio")
	@Size(min = 2, max = 100, message = "O atributo NAME deve conter no minimo 2 e no maximo 100 caracteres ")
	private String name;
	
	@Schema(example = "email@email.com.br")
	@NotNull(message = "O Atributo Email é Obrigatório!")
	@Email(message = "O Atributo Usuário deve ser um email válido!")
	private String email;
	
	@NotBlank(message = "O atributo PASSWORD é Obrigatorio")
	@Size(min = 6, max = 100, message = "O atributo PASSWORD deve conter no minimo 6 e no maximo 50 caracteres ")
	private String password;
	
	@Size(min = 2, max = 5000, message = "O atributo PHOTO deve conter no minimo 2 e no maximo 5000 caracteres ")
	private String photo;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("post")
	private List<Post> post;
	
	public User(Long id, String name, String email, String password, String photo) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.photo = photo;
	}
	
	public User() { }
	
	
	public void setPost(List<Post> post) {
		this.post = post;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public List<Post> getPost() {
		return post;
	}
	
	

}
