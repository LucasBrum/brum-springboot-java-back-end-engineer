package com.brum.client.school.curriculumgrid.service;

import java.util.List;

import com.brum.client.school.curriculumgrid.dto.CourseDto;
import com.brum.client.school.curriculumgrid.entity.Course;

public interface CourseService {

	public Boolean create(CourseDto courseDto);
	
	public Boolean update(CourseDto courseDto);
	
	public Boolean delete(Long id);
	
	public List<Course> listAll();
	
	public Course findByCode(String code);
	
	public Course findById(Long id);
	
}
