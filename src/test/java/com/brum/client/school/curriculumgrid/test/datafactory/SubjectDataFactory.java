package com.brum.client.school.curriculumgrid.test.datafactory;

import com.brum.client.school.curriculumgrid.dto.SubjectDto;
import com.brum.client.school.curriculumgrid.entity.Subject;

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
	
	public static SubjectDto buildDtoToCreate() {
		
		SubjectDto subjectDto = new SubjectDto();
		subjectDto.setName("Introdução a Linguagem de Programação");
		subjectDto.setCode("ILP");
		subjectDto.setFrequency(1);
		subjectDto.setHours(100);
		
		return subjectDto;
	}
	
	public static Subject buildToCreate() {
		Subject subject = new Subject();
		subject.setName("Introdução a Linguagem de Programação");
		subject.setCode("ILP");
		subject.setFrequency(1);
		subject.setHours(64);
		
		return subject;
	}
	
	public static Subject build() {
		Subject subject = new Subject();
		subject.setId(1L);
		subject.setName("Introdução a Linguagem de Programação");
		subject.setCode("ILP");
		subject.setFrequency(1);
		subject.setHours(64);
		
		return subject;
	}
}
