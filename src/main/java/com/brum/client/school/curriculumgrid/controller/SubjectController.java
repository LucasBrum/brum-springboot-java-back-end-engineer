package com.brum.client.school.curriculumgrid.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
import com.brum.client.school.curriculumgrid.dto.SubjectDto;
import com.brum.client.school.curriculumgrid.enums.HyperLinkConstant;
import com.brum.client.school.curriculumgrid.model.Response;
import com.brum.client.school.curriculumgrid.service.SubjectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/subjects")
@Api(tags = SwaggerConfig.SUBJECT)
public class SubjectController {
	
	@Autowired
	private SubjectService subjectService;
	
	@GetMapping
	@ApiOperation(value = "List all subjects")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Subject Listed with success"),
			@ApiResponse(code = 500, message = "Internal Server Error")

	})
	public ResponseEntity<Response<List<SubjectDto>>> listAll() {
		
		List<SubjectDto> subjectsList = this.subjectService.listAll();

		Response<List<SubjectDto>> response = new Response<>();
		
		response.setData(subjectsList);
		response.setStatusCode(HttpStatus.OK.value());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SubjectController.class).listAll()).withSelfRel());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	@PostMapping
	@ApiOperation(value = "Create a new Subject")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Subject created with success"),
			@ApiResponse(code = 500, message = "Internal Server Error")

	})
	public ResponseEntity<Response<Boolean>> create(@Valid @RequestBody SubjectDto subjectDto) {
		Boolean isSubjectCreated = this.subjectService.create(subjectDto);
		
		Response<Boolean> response = new Response<>();
		response.setData(isSubjectCreated);
		response.setStatusCode(HttpStatus.CREATED.value());
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Find Subject by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Subject Found with success"),
			@ApiResponse(code = 400, message = "Request error sent by client"),
			@ApiResponse(code = 404, message = "Subject not found"),
			@ApiResponse(code = 500, message = "Internal Server Error")

	})
	public ResponseEntity<Response<SubjectDto>> findById(@PathVariable Long id) {
		
		SubjectDto subjectDto = this.subjectService.findById(id);
		
		Response<SubjectDto> response = new Response<>();
		response.setData(subjectDto);
		response.setStatusCode(HttpStatus.OK.value());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SubjectController.class).findById(id)).withSelfRel());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SubjectController.class).delete(id)).withRel(HyperLinkConstant.DELETE.getValue()));
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SubjectController.class).update(subjectDto)).withRel(HyperLinkConstant.DELETE.getValue()));
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PutMapping
	@ApiOperation(value = "Update Subject")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Subject updated with success"),
			@ApiResponse(code = 404, message = "Subject not found"),
			@ApiResponse(code = 500, message = "Internal Server Error")
			
			

	})
	public ResponseEntity<Response<Boolean>> update(@Valid @RequestBody SubjectDto subjectDto) {
		
		Boolean isSubjectUpdated = this.subjectService.update(subjectDto);
		
		Response<Boolean> response = new Response<>();
		response.setData(isSubjectUpdated);
		response.setStatusCode(HttpStatus.OK.value());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete Subject")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Subject deleted with success"),
			@ApiResponse(code = 400, message = "Request error sent by user"),
			@ApiResponse(code = 500, message = "Internal Server Error")

	})
	public ResponseEntity<Response<Boolean>> delete(@PathVariable Long id) {
		Boolean isSubjectDeleted = this.subjectService.delete(id);
		
		Response<Boolean> response = new Response<>();
		response.setData(isSubjectDeleted);
		response.setStatusCode(HttpStatus.OK.value());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	@GetMapping("/min-hour/{minHour}")
	@ApiOperation(value = "Find Subject by min hour")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Subject Found with success"),
			@ApiResponse(code = 400, message = "Request error sent by client"),
			@ApiResponse(code = 404, message = "Subject not found"),
			@ApiResponse(code = 500, message = "Internal Server Error")

	})
	public ResponseEntity<Response<List<SubjectDto>>> findByMinHour(@PathVariable int minHour) {
		
		Response<List<SubjectDto>> response = new Response<>();
		List<SubjectDto> subjectsList = this.subjectService.findByMinHour(minHour);
		
		response.setData(subjectsList);
		response.setStatusCode(HttpStatus.OK.value());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/min-frequency/{frequency}")
	@ApiOperation(value = "Find Subject by frequency")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Subject Found with success"),
			@ApiResponse(code = 400, message = "Request error sent by client"),
			@ApiResponse(code = 404, message = "Subject not found"),
			@ApiResponse(code = 500, message = "Internal Server Error")

	})
	public ResponseEntity<Response<List<SubjectDto>>> findByFrequency(@PathVariable int frequency) {
		Response<List<SubjectDto>> response = new Response<>();
		List<SubjectDto> subjectsList = this.subjectService.findByFrequency(frequency);
		
		response.setData(subjectsList);
		response.setStatusCode(HttpStatus.OK.value());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
}
