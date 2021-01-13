package com.brum.client.school.curriculumgrid.test.datafactory;

import java.util.ArrayList;
import java.util.List;

import com.brum.client.school.curriculumgrid.entity.Course;
import com.brum.client.school.curriculumgrid.entity.Subject;

public abstract class CourseDataFactory {

	private CourseDataFactory() {
	}

	public static Course build() {
		List<Subject> subjects = new ArrayList<>();
		subjects.add(SubjectDataFactory.build());

		Course course = new Course();
		course.setId(1L);
		course.setName("Algoritmos");
		course.setCode("Alg");
		course.setSubjects(subjects);
		return course;

	}
}
