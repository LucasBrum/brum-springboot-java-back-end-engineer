package com.brum.client.school.curriculumgrid.controller;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brum.client.school.curriculumgrid.dto.CategoryDTO;
import com.brum.client.school.curriculumgrid.entity.Category;
import com.brum.client.school.curriculumgrid.entity.User;
import com.brum.client.school.curriculumgrid.model.Response;
import com.brum.client.school.curriculumgrid.repository.CategoryRepository;
import com.brum.client.school.curriculumgrid.service.UserInfoService;

@RestController
@RequestMapping({"/categories","/v2/categories"})
public class CategoryController {

	private ModelMapper mapper = new ModelMapper();

	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@PostMapping
	public ResponseEntity<Response<Category>> createCategory(@RequestBody CategoryDTO categoryDto) {

		Response<Category> response = new Response<>();

		try {

			User user = userInfoService.getAuthenticatedUser();

			if (categoryDto != null && categoryDto.getId() == null) {
				Category category = this.categoryRepository.save(mapper.map(categoryDto, Category.class));
				category.setUser(user);
				response.setData(this.categoryRepository.save(category));
				response.setStatusCode(HttpStatus.CREATED.value());

				return ResponseEntity.status(HttpStatus.CREATED).body(response);
			}
			throw new Exception();
		} catch (Exception e) {
			response.setData(null);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PutMapping
	public ResponseEntity<Response<Category>> updateCategory(@RequestBody CategoryDTO categoryDto) {
		Response<Category> response = new Response<>();
		try {
			
			User user = userInfoService.getAuthenticatedUser();
			
			if (categoryDto != null && categoryDto.getId() != null) {
				Optional<Category> category = this.categoryRepository.findById(categoryDto.getId());
				if (category.isPresent() && category.get().getUser().getId().equals(user.getId())) {
					categoryDto.setUser(user);
					response.setData(this.categoryRepository.save(mapper.map(categoryDto, Category.class)));
					response.setStatusCode(HttpStatus.OK.value());
					return ResponseEntity.status(HttpStatus.OK).body(response);
				}
			}
			throw new Exception();
		} catch (Exception e) {
			response.setData(null);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}

	@GetMapping
	public ResponseEntity<Response<List<Category>>> listAllCategories() {
		Response<List<Category>> response = new Response<>();
		try {
			User user = userInfoService.getAuthenticatedUser();
			response.setData(this.categoryRepository.findAllByUserId(user.getId()));
			response.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			response.setData(null);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<Response<Category>> consultCategory(@PathVariable Long id) {
		Response<Category> response = new Response<>();
		try {
			Optional<Category> category = this.categoryRepository.findById(id);
			if (category.isPresent()) {
				response.setData(category.get());
			}
			response.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			response.setData(null);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Boolean>> deleteCategory(@PathVariable Long id) {
		Response<Boolean> response = new Response<>();
		try {
			if (this.categoryRepository.findById(id).isPresent()) {
				this.categoryRepository.deleteById(id);
				response.setData(Boolean.TRUE);
			}
			response.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			response.setData(null);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}

}
