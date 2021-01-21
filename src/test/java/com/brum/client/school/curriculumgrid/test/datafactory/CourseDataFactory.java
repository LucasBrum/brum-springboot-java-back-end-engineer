package com.brum.client.school.curriculumgrid.test.datafactory;

import java.util.ArrayList;
import java.util.List;

import com.brum.client.school.curriculumgrid.dto.CourseDto;
import com.brum.client.school.curriculumgrid.entity.Course;
import com.brum.client.school.curriculumgrid.entity.Subject;

public abstract class CourseDataFactory {

	private CourseDataFactory() {
	}

	public static Course build() {
		List<Subject> subjects = buildListSubjects();

		Course course = new Course();
		course.setId(1L);
		course.setName("Sistema de Informação");
		course.setCode("SI");
		course.setSubjects(subjects);
		return course;

	}

	public static CourseDto buildDto() {
		List<Long> listSubjectsId = new ArrayList<>();
		listSubjectsId.add(1L);

		CourseDto courseDto = new CourseDto();
		courseDto.setId(1L);
		courseDto.setName("Engenharia de Software");
		courseDto.setCode("ESW");
		courseDto.setSubjects(listSubjectsId);

		return courseDto;
	}

	private static List<Subject> buildListSubjects() {
		List<Subject> subjects = new ArrayList<>();
		subjects.add(SubjectDataFactory.build());
		return subjects;
	}
}
