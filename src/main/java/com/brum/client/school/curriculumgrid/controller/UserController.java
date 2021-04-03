package com.brum.client.school.curriculumgrid.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brum.client.school.curriculumgrid.dto.UserDTO;
import com.brum.client.school.curriculumgrid.entity.User;
import com.brum.client.school.curriculumgrid.model.Response;
import com.brum.client.school.curriculumgrid.repository.UserRepository;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder pass;
	
	@PostMapping
	public ResponseEntity<Response<User>> cadastrarUsuario(@RequestBody UserDTO userDto) {
		Response<User> response = new Response<>();
		
		try {
			
			User user = new User();
			
			user.setName(userDto.getName());
			user.getCredential().setEmail(userDto.getEmail());
			user.getCredential().setPassword(pass.encode(userDto.getPassword()));
			
			response.setData(this.userRepository.save(user));
			response.setStatusCode(HttpStatus.CREATED.value());
			
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
			
		} catch (Exception e) {
			response.setData(null);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}
	
	@GetMapping
	public ResponseEntity<Response<List<User>>> listar() {
		Response<List<User>> response = new Response<>();
		try {
			response.setData(this.userRepository.findAll());
			response.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			response.setData(null);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}
	
}
