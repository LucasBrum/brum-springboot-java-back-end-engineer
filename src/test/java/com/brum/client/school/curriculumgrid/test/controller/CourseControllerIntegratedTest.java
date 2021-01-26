package com.brum.client.school.curriculumgrid.test.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.brum.client.school.curriculumgrid.dto.CourseDto;
import com.brum.client.school.curriculumgrid.entity.Course;
import com.brum.client.school.curriculumgrid.entity.Subject;
import com.brum.client.school.curriculumgrid.model.Response;
import com.brum.client.school.curriculumgrid.repository.CourseRepository;
import com.brum.client.school.curriculumgrid.test.datafactory.CourseDataFactory;
import com.brum.client.school.curriculumgrid.test.datafactory.SubjectDataFactory;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseControllerIntegratedTest {

	@LocalServerPort
	private int port;
		
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@BeforeEach
	public void init() {
		this.buildDatabase();
	}
	
	@AfterEach
	public void finish() {
		this.courseRepository.deleteAll();
	}

	private void buildDatabase() {
		List<Subject> subjects = SubjectDataFactory.buildList();
		
		Course course1 = new Course();
		course1.setName("Engenharia da Computação");
		course1.setCode("EC");
		course1.setSubjects(subjects);
		
		Course course2 = new Course();
		course2.setName("Sistema de Informação");
		course2.setCode("SI");
		course2.setSubjects(subjects);
		
		this.courseRepository.saveAll(Arrays.asList(course1, course2));
	}
	
	@Test
	public void testListAllCourses() {
		ResponseEntity<Response<List<Course>>> courses = restTemplate.exchange(
				"http://localhost:" + this.port + "/courses/", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<Course>>>() {
				});
		
		assertNotNull(courses.getBody().getData());
		assertEquals(2, courses.getBody().getData().size());
		assertEquals(200, courses.getBody().getStatusCode());
	}
	
	@Test
	public void testCreateCourse() {
		CourseDto courseDto = CourseDataFactory.buildDtoToCreate();
		
		HttpEntity<CourseDto> request = new HttpEntity<>(courseDto);
		
		ResponseEntity<Response<Boolean>> courses = restTemplate.exchange(
				"http://localhost:" + this.port + "/courses/", HttpMethod.POST, request,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		
		List<Course> coursesList = this.courseRepository.findAll();
		
		assertNotNull(courses.getBody().getData());
		assertEquals(3, coursesList.size());
		assertEquals(201, courses.getBody().getStatusCode());
	}
	
	@Test
	public void testFindCourseById() {
		List<Course> courseList = this.courseRepository.findAll();
		Long id = courseList.get(0).getId();
		
		ResponseEntity<Response<Course>> course = restTemplate.exchange(
				"http://localhost:" +this.port + "/courses/"+id, HttpMethod.GET, null, 
				new ParameterizedTypeReference<Response<Course>>() {
				});
		
		assertNotNull(course.getBody().getData());
		assertEquals("EC", course.getBody().getData().getCode());
		assertEquals(200, course.getBody().getStatusCode());
	}
	
	@Test
	public void testFindCourseByCode() {
		ResponseEntity<Response<Course>> course = restTemplate.exchange(
				"http://localhost:" +this.port + "/courses/code/EC", HttpMethod.GET, null, 
				new ParameterizedTypeReference<Response<Course>>() {
				});
		
		assertNotNull(course.getBody().getData());
		assertEquals("EC", course.getBody().getData().getCode());
		assertEquals(200, course.getBody().getStatusCode());
	}
	
	@Test
	public void testUpdateCourse() {
		List<Course> courseList = this.courseRepository.findAll();
		Course course = courseList.get(0);
		
		course.setName("Test Course Updated");
		
		HttpEntity<Course> request = new HttpEntity<>(course);
		
		ResponseEntity<Response<Boolean>> courses = restTemplate.exchange(
				"http://localhost:" + this.port + "/courses/", HttpMethod.PUT, request,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		
		Course courseUpdated = this.courseRepository.findById(course.getId()).get();
		
		assertNotNull(courses.getBody().getData());
		assertEquals("Test Course Updated", courseUpdated.getName());
		assertEquals(200, courses.getBody().getStatusCode());
	}
	
}
