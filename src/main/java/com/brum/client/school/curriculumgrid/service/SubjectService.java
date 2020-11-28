package com.brum.client.school.curriculumgrid.service;

import java.util.List;

import com.brum.client.school.curriculumgrid.entity.Subject;

public interface SubjectService {

	public Boolean create(Subject subject);
	
	public Subject findById(Long id);
	
	public List<Subject> listAll();
	
	public Subject update(Subject subject);
	
	public Boolean delete(Long id);
}
