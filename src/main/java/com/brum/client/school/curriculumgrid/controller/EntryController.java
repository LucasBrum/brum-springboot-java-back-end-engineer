package com.brum.client.school.curriculumgrid.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brum.client.school.curriculumgrid.dto.EntryDTO;
import com.brum.client.school.curriculumgrid.entity.Category;
import com.brum.client.school.curriculumgrid.entity.Entry;
import com.brum.client.school.curriculumgrid.entity.User;
import com.brum.client.school.curriculumgrid.model.Response;
import com.brum.client.school.curriculumgrid.repository.CategoryRepository;
import com.brum.client.school.curriculumgrid.repository.EntryRepository;
import com.brum.client.school.curriculumgrid.service.UserInfoService;

@RestController
@RequestMapping("/entries")
@PreAuthorize(value = "#oauth2.hasScope('cw_logged') and hasAnyRole('ROLE_CUSTOMER','ROLE_ADMIN')")
public class EntryController {
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private EntryRepository entryRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@PostMapping
	public ResponseEntity<Response<Entry>> createEntry(@RequestBody EntryDTO entryDto) {
		Response<Entry> response = new Response<>();
		try {
			
			User user = this.userInfoService.getAuthenticatedUser();
			Optional<Category> category = this.categoryRepository.findByUserId(entryDto.getCategoryId(), user.getId());
			if (entryDto.getId() == null && category.isPresent()) {

				Entry entry = new Entry();

				entry.setCategory(category.get());
				entry.setType(entryDto.getType());
				entry.setValue(entryDto.getValue());
				entry.setUser(user);
				response.setData(this.entryRepository.save(entry));
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
	public ResponseEntity<Response<Entry>> updateEntry(@RequestBody EntryDTO entryDto) {
		Response<Entry> response = new Response<>();
		try {
			User user = this.userInfoService.getAuthenticatedUser();
			Optional<Category> category = this.categoryRepository.findByUserId(entryDto.getCategoryId(), user.getId());
			Optional<Entry> entryFounded = this.entryRepository.findById(entryDto.getId());
			if (entryFounded.isPresent() && category.isPresent()) {
				Entry entry = entryFounded.get();
				
				entry.setCategory(category.get());
				entry.setType(entryDto.getType());
				entry.setValue(entryDto.getValue());
				
				response.setData(this.entryRepository.save(entry));
				response.setStatusCode(HttpStatus.OK.value());
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
			throw new Exception();
		} catch (Exception e) {
			response.setData(null);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}

	@GetMapping
	@PreAuthorize(value = "#oauth2.hasAnyScope('cc_logged','cw_logged')")
	public ResponseEntity<Response<List<Entry>>> listEntries() {
		Response<List<Entry>> response = new Response<>();
		try {
			User user = this.userInfoService.getAuthenticatedUser();
			
			response.setData(this.entryRepository.findAllByUserId(user.getId()));
			response.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			response.setData(null);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}

	@GetMapping("/{id}")
	@PreAuthorize(value = "#oauth2.hasAnyScope('cc_logged','cw_logged')")
	public ResponseEntity<Response<Entry>> consultEntry(@PathVariable("id") long id) {
		Response<Entry> response = new Response<>();
		try {
			Optional<Entry> entry = this.entryRepository.findById(id);
			if (entry.isPresent()) {
				response.setData(entry.get());
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
	public ResponseEntity<Response<Boolean>> excluirLancamento(@PathVariable("id") long id) {
		Response<Boolean> response = new Response<>();
		try {
			if (this.entryRepository.findById(id).isPresent()) {
				this.entryRepository.deleteById(id);
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
