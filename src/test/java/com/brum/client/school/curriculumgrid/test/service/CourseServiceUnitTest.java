package com.brum.client.school.curriculumgrid.test.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.brum.client.school.curriculumgrid.dto.CourseDto;
import com.brum.client.school.curriculumgrid.entity.Course;
import com.brum.client.school.curriculumgrid.entity.Subject;
import com.brum.client.school.curriculumgrid.enums.ExceptionMessageEnum;
import com.brum.client.school.curriculumgrid.exception.CourseException;
import com.brum.client.school.curriculumgrid.repository.CourseRepository;
import com.brum.client.school.curriculumgrid.repository.SubjectRepository;
import com.brum.client.school.curriculumgrid.service.impl.CourseServiceImpl;
import com.brum.client.school.curriculumgrid.test.datafactory.CourseDataFactory;
import com.brum.client.school.curriculumgrid.test.datafactory.SubjectDataFactory;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
public class CourseServiceUnitTest {

	@Mock
	private CourseRepository courseRepository;

	@Mock
	private SubjectRepository subjectRepository;

	@InjectMocks
	private CourseServiceImpl courseService;

	private static Course course;
	private static Subject subject;

	@BeforeAll
	public static void init() {
		course = CourseDataFactory.build();
		subject = SubjectDataFactory.build();
	}

	@Test
	public void testListAllSuccess() {
		List<Course> courses = new ArrayList<>();
		courses.add(course);

		Mockito.when(this.courseRepository.findAll()).thenReturn(courses);

		List<Course> coursesList = this.courseService.listAll();

		assertNotNull(coursesList);
		assertEquals("Sistema de Informação", coursesList.get(0).getName());

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

	@Test
	public void testCreateSuccess() {
		List<Subject> subjectsList = SubjectDataFactory.buildList();
		Course courseToSave = CourseDataFactory.buildToCreate();
		Optional<Subject> subjectOptional = Optional.of(subjectsList.get(0));

		CourseDto courseDto = CourseDataFactory.buildDtoToCreate();

		Mockito.when(this.subjectRepository.findById(1L)).thenReturn(subjectOptional);
		lenient().when(this.courseRepository.save(courseToSave)).thenReturn(courseToSave);

		Boolean isCourseSaved = this.courseService.create(courseDto);

		assertTrue(isCourseSaved);

	}

	@Test
	public void testCreateThrowCourseException() {
		List<Subject> subjectsList = SubjectDataFactory.buildList();
		Course courseToSave = CourseDataFactory.buildToCreate();
		Optional<Subject> subjectOptional = Optional.of(subjectsList.get(0));

		CourseDto courseDto = CourseDataFactory.buildDtoToCreate();

		Mockito.when(this.subjectRepository.findById(1L)).thenReturn(subjectOptional);
		Mockito.when(this.courseRepository.save(courseToSave)).thenThrow(IllegalStateException.class);

		CourseException courseException;

		courseException = assertThrows(CourseException.class, () -> {
			this.courseService.create(courseDto);
		});

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, courseException.getHttpStatus());
		assertEquals(ExceptionMessageEnum.INTERNAL_ERROR.getValue(), courseException.getMessage());

	}

	@Test
	public void testUpdateSuccess() {
		CourseDto courseDto = CourseDataFactory.buildDto();
		
		Mockito.when(this.subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
		lenient().when(this.courseRepository.findById(1L)).thenReturn(Optional.of(course));
		lenient().when(this.courseRepository.save(course)).thenReturn(course);

		Boolean isCourseUpdated = this.courseService.update(courseDto);

		assertTrue(isCourseUpdated);

	}

	@Test
	public void testUpdateThrowCourseException() {
		CourseDto courseDto = CourseDataFactory.buildDto();

		lenient().when(this.courseRepository.findById(1L)).thenReturn(Optional.empty());
		Mockito.when(this.subjectRepository.findById(1L)).thenThrow(IllegalStateException.class);

		CourseException courseException;

		courseException = assertThrows(CourseException.class, () -> {
			this.courseService.update(courseDto);
		});

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, courseException.getHttpStatus());

		Mockito.verify(this.courseRepository, times(0)).save(course);

	}

	@Test
	public void testUpdateThrowException() {
		CourseDto courseDto = CourseDataFactory.buildDto();

		Mockito.when(this.courseRepository.findById(1L)).thenReturn(Optional.of(course));
		Mockito.when(this.courseRepository.save(course)).thenThrow(IllegalStateException.class);

		CourseException courseException;

		courseException = assertThrows(CourseException.class, () -> {
			this.courseService.update(courseDto);
		});

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, courseException.getHttpStatus());
		assertEquals(ExceptionMessageEnum.INTERNAL_ERROR.getValue(), courseException.getMessage());

		Mockito.verify(this.courseRepository, times(0)).save(course);

	}

	@Test
	public void testDeleteSuccess() {
		Mockito.when(this.courseRepository.findById(1L)).thenReturn(Optional.of(course));

		Boolean isCourseDeleted = this.courseService.delete(1L);

		assertTrue(isCourseDeleted);
	}

	@Test
	public void testDeleteThrowCourseException() {
		Mockito.when(this.courseRepository.findById(1L)).thenThrow(IllegalStateException.class);

		CourseException courseException;

		courseException = assertThrows(CourseException.class, () -> {
			this.courseService.delete(1L);
		});

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, courseException.getHttpStatus());

		Mockito.verify(this.courseRepository, times(1)).findById(1L);
		Mockito.verify(this.courseRepository, times(0)).save(course);
	}

	@Test
	public void testDeleteThrowException() {
		Mockito.when(this.courseRepository.findById(1L)).thenReturn(Optional.empty());

		CourseException courseException;

		courseException = assertThrows(CourseException.class, () -> {
			this.courseService.delete(1L);
		});

		assertEquals(HttpStatus.NOT_FOUND, courseException.getHttpStatus());
		assertEquals(ExceptionMessageEnum.COURSE_NOT_FOUND.getValue(), courseException.getMessage());

		Mockito.verify(this.courseRepository, times(1)).findById(1L);
		Mockito.verify(this.courseRepository, times(0)).save(course);
	}

	@Test
	public void testFindByCodeSuccess() {
		Mockito.when(this.courseRepository.findCourseByCode("SI")).thenReturn(course);

		Course course = this.courseService.findByCode("SI");

		assertNotNull(course);
	}
	
	@Test
	public void testFindByCodeThrowCourseException() {
		Mockito.when(this.courseRepository.findCourseByCode("SI")).thenThrow(IllegalStateException.class);
		
		CourseException courseException;

		courseException = assertThrows(CourseException.class, () -> {
			this.courseService.findByCode("SI");
		});

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, courseException.getHttpStatus());

		Mockito.verify(this.courseRepository, times(1)).findCourseByCode("SI");
	}

}
