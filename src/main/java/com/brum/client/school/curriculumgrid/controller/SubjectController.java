package com.brum.client.school.curriculumgrid.controller;

import java.util.List;

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

import com.brum.client.school.curriculumgrid.entity.Subject;
import com.brum.client.school.curriculumgrid.service.SubjectService;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;
	
	@GetMapping
	public ResponseEntity<List<Subject>> listAll() {
		List<Subject> listSubjects = this.subjectService.listAll();
		return ResponseEntity.status(HttpStatus.OK).body(listSubjects);
		
	}
	
	@PostMapping
	public ResponseEntity<Boolean> create(@RequestBody Subject subject) {
		Boolean isSubjectCreated = this.subjectService.create(subject);
		
		return ResponseEntity.status(HttpStatus.OK).body(isSubjectCreated);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Subject> findById(@PathVariable Long id) {
		
		Subject subject = this.subjectService.findById(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(subject);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Subject> update(@RequestBody Subject subject) {
		
		Subject subjectAltered = this.subjectService.update(subject);
		
		return ResponseEntity.status(HttpStatus.OK).body(subjectAltered);
		
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		
		this.subjectService.delete(id);
		
	}
	
}
