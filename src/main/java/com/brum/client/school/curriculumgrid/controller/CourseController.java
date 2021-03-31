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

import com.brum.client.school.curriculumgrid.config.SwaggerConfig;
import com.brum.client.school.curriculumgrid.dto.CourseDto;
import com.brum.client.school.curriculumgrid.entity.Course;
import com.brum.client.school.curriculumgrid.model.Response;
import com.brum.client.school.curriculumgrid.service.CourseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/courses")
@Api(tags = SwaggerConfig.COURSE)
public class CourseController {

	@Autowired
	private CourseService courseService;

	@PostMapping
	@ApiOperation(value = "Create a new course")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Course created with success"),
			@ApiResponse(code = 500, message = "Internal Server Error")

	})
	public ResponseEntity<Response<Boolean>> create(@Valid @RequestBody CourseDto courseDto) {
		Boolean isCourseCreated = this.courseService.create(courseDto);

		Response<Boolean> response = new Response<>();
		response.setData(isCourseCreated);
		response.setStatusCode(HttpStatus.CREATED.value());

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	@ApiOperation(value = "List all courses")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Course Listed with success"),
			@ApiResponse(code = 500, message = "Internal Server Error")

	})
	public ResponseEntity<Response<List<Course>>> listAll() {
		List<Course> coursesList = this.courseService.listAll();

		Response<List<Course>> response = new Response<>();

		response.setData(coursesList);
		response.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Find Course by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Course Found with success"),
			@ApiResponse(code = 400, message = "Request error sent by client"),
			@ApiResponse(code = 404, message = "Course not found"),
			@ApiResponse(code = 500, message = "Internal Server Error")

	})
	public ResponseEntity<Response<Course>> findById(@PathVariable Long id) {

		Course course = this.courseService.findById(id);

		Response<Course> response = new Response<>();
		response.setData(course);
		response.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	@GetMapping("/code/{code}")
	@ApiOperation(value = "Find Course by code")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Course found with success"),
			@ApiResponse(code = 500, message = "Internal Server Error")

	})
	public ResponseEntity<Response<Course>> findByCode(@PathVariable String code) {
		Course course = this.courseService.findByCode(code);

		Response<Course> response = new Response<>();
		response.setData(course);
		response.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping
	@ApiOperation(value = "Update Course")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Course updated with success"),
			@ApiResponse(code = 404, message = "Course not found"),
			@ApiResponse(code = 500, message = "Internal Server Error")
			
			

	})
	public ResponseEntity<Response<Boolean>> update(@Valid @RequestBody CourseDto courseDto) {
		Boolean isCourseUpdated = this.courseService.update(courseDto);

		Response<Boolean> response = new Response<>();
		response.setData(isCourseUpdated);
		response.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete Course")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Course deleted with success"),
			@ApiResponse(code = 400, message = "Request error sent by user"),
			@ApiResponse(code = 500, message = "Internal Server Error")

	})
	public ResponseEntity<Response<Boolean>> delete(@PathVariable Long id) {

		Boolean isCourseDeleted = this.courseService.delete(id);

		Response<Boolean> response = new Response<>();
		response.setData(isCourseDeleted);
		response.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
