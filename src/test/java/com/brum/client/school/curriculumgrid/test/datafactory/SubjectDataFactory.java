package com.brum.client.school.curriculumgrid.test.datafactory;

import java.util.ArrayList;
import java.util.List;

import com.brum.client.school.curriculumgrid.dto.SubjectDto;
import com.brum.client.school.curriculumgrid.entity.Subject;

public abstract class SubjectDataFactory {

	private SubjectDataFactory() {
	}

	public static SubjectDto buildDto() {

		SubjectDto subjectDto = new SubjectDto();
		subjectDto.setId(1L);
		subjectDto.setName("Introdução a Linguagem de Programação");
		subjectDto.setCode("ILP");
		subjectDto.setFrequency(1);
		subjectDto.setHours(100);

		return subjectDto;
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

	public static List<Subject> buildList() {
		List<Subject> subjects = new ArrayList<>();

		Subject subject1 = new Subject();
		subject1.setId(1L);
		subject1.setName("Introdução a Linguagem de Programação");
		subject1.setCode("ILP");
		subject1.setFrequency(1);
		subject1.setHours(64);

		Subject subject2 = new Subject();
		subject2.setId(2L);
		subject2.setName("Banco de Dados");
		subject2.setCode("BD");
		subject2.setFrequency(1);
		subject2.setHours(64);

		subjects.add(subject1);
		subjects.add(subject2);

		return subjects;

	}

}
