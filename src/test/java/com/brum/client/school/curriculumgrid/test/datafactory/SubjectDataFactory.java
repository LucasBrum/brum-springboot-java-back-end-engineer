package com.brum.client.school.curriculumgrid.test.datafactory;

import com.brum.client.school.curriculumgrid.dto.SubjectDto;

public abstract class SubjectDataFactory {

	private SubjectDataFactory() {}
	
	public static SubjectDto buildDto() {
		
		SubjectDto subjectDto = new SubjectDto();
		subjectDto.setId(1L);
		subjectDto.setName("Introdução a Linguagem de Programação");
		subjectDto.setCode("ILP");
		subjectDto.setFrequency(1);
		subjectDto.setHours(100);
		
		return subjectDto;
	}
}
