package com.brum.client.school.curriculumgrid.service;

import java.util.List;

import com.brum.client.school.curriculumgrid.dto.SubjectDto;

public interface SubjectService {

	public Boolean create(SubjectDto subjectDto);
	
	public SubjectDto findById(Long id);
	
	public List<SubjectDto> listAll();
	
	public Boolean update(SubjectDto subjectDto);
	
	public Boolean delete(Long id);

	public List<SubjectDto> findByMinHour(int minHour);

	public List<SubjectDto> findByFrequency(int frequency);
}
