package com.brum.client.school.curriculumgrid.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.brum.client.school.curriculumgrid.dto.CourseDto;
import com.brum.client.school.curriculumgrid.entity.Course;
import com.brum.client.school.curriculumgrid.model.Response;
import com.brum.client.school.curriculumgrid.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	
	@GetMapping
	public ResponseEntity<Response<List<Course>>> listAll() {
		List<Course> coursesList = this.courseService.listAll();
		
		Response<List<Course>> response = new Response<>();
		
		response.setData(coursesList);
		response.setStatusCode(HttpStatus.OK.value());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	
	@PostMapping
	public ResponseEntity<Boolean> create(@Valid @RequestBody CourseDto courseDto) {
		
		Boolean isCourseCreated = this.courseService.create(courseDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(isCourseCreated);
	}
	

	@GetMapping("/{id}")
	public ResponseEntity<Response<CourseDto>> findById(@PathVariable Long id) {
		
		CourseDto courseDto = this.courseService.findById(id);
		
		Response<CourseDto> response = new Response<>();
		response.setData(courseDto);
		response.setStatusCode(HttpStatus.OK.value());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	@PutMapping
	public ResponseEntity<Boolean> update(@Valid @RequestBody CourseDto courseDto) {
		Boolean isCourseUpdated = this.courseService.update(courseDto);
		
		return ResponseEntity.status(HttpStatus.OK).body(isCourseUpdated);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable Long id) {
		
		return ResponseEntity.status(HttpStatus.OK).body(this.courseService.delete(id));
	}
	
}
