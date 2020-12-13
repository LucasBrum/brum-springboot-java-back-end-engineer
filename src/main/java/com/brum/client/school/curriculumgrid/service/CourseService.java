package com.brum.client.school.curriculumgrid.service;

import java.util.List;

import com.brum.client.school.curriculumgrid.dto.CourseDto;

public interface CourseService {

	public Boolean create(CourseDto courseDto);
	
	public Boolean update(CourseDto courseDto);
	
	public Boolean delete(Long id);
	
	public List<CourseDto> listAll();
	
	public List<CourseDto> findByCode(String code);
	
	public CourseDto findById(Long id);
	
	
}
