package com.brum.client.school.curriculumgrid.test.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.brum.client.school.curriculumgrid.entity.Course;
import com.brum.client.school.curriculumgrid.exception.CourseException;
import com.brum.client.school.curriculumgrid.repository.CourseRepository;
import com.brum.client.school.curriculumgrid.service.impl.CourseServiceImpl;
import com.brum.client.school.curriculumgrid.test.datafactory.CourseDataFactory;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
public class CourseServiceUnitTest {
	
	@Mock
	private CourseRepository courseRepository;
	
	@InjectMocks
	private CourseServiceImpl courseService;
	
	private static Course course;
	
	@BeforeAll
	public static void init() {
		course = CourseDataFactory.build();
	}
	
	@Test
	public void testListAllSuccess() {
		List<Course> courses = new ArrayList<>();
		courses.add(course);
		
		Mockito.when(this.courseRepository.findAll()).thenReturn(courses);
		
		List<Course> coursesList = this.courseService.listAll();
		
		assertNotNull(coursesList);
		assertEquals(coursesList.get(0).getName(), "Algoritmos");
		
		Mockito.verify(this.courseRepository, times(1)).findAll();
	}
	
	@Test
	public void testListAllThrowException() {
		List<Course> courses = new ArrayList<>();
		courses.add(course);
		
		Mockito.when(this.courseRepository.findAll()).thenThrow(IllegalStateException.class);
		
		CourseException courseException;
		
		courseException = assertThrows(CourseException.class, () -> {
			this.courseService.listAll();
		});
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, courseException.getHttpStatus());
		
		Mockito.verify(this.courseRepository, times(1)).findAll();
	}

}
