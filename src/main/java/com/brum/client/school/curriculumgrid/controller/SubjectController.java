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

import com.brum.client.school.curriculumgrid.dto.SubjectDto;
import com.brum.client.school.curriculumgrid.service.SubjectService;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;
	
	@GetMapping
	public ResponseEntity<List<SubjectDto>> listAll() {
		List<SubjectDto> listSubjects = this.subjectService.listAll();
		return ResponseEntity.status(HttpStatus.OK).body(listSubjects);
		
	}
	
	@PostMapping
	public ResponseEntity<Boolean> create(@Valid @RequestBody SubjectDto subjectDto) {
		Boolean isSubjectCreated = this.subjectService.create(subjectDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(isSubjectCreated);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SubjectDto> findById(@PathVariable Long id) {
		
		SubjectDto subjectDto = this.subjectService.findById(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(subjectDto);
	}
	
	@PutMapping
	public ResponseEntity<Boolean> update(@Valid @RequestBody SubjectDto subjectDto) {
		
		Boolean isSubjectUpdated = this.subjectService.update(subjectDto);
		
		return ResponseEntity.status(HttpStatus.OK).body(isSubjectUpdated);
		
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		
		this.subjectService.delete(id);
		
	}
	
}
