package com.brum.client.school.curriculumgrid.test.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
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
import com.brum.client.school.curriculumgrid.repository.SubjectRepository;
import com.brum.client.school.curriculumgrid.test.datafactory.CourseDataFactory;

@RunWith(JUnitPlatform.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseControllerIntegratedTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@BeforeEach
	public void init() {
		this.buildDatabase();
	}

	@AfterEach
	public void finish() {
		this.courseRepository.deleteAll();
		this.subjectRepository.deleteAll();
	}

	private void buildDatabase() {
		createSubjectsInDatabase();
		buildCourseInDataBase();
	}

	private void buildCourseInDataBase() {
		List<Subject> subjects = this.subjectRepository.findAll();

		Course course1 = new Course();
		course1.setName("Engenharia da Computação");
		course1.setCode("EC");
		course1.setSubjects(subjects);

		this.courseRepository.save(course1);

	}

	private void createSubjectsInDatabase() {
		Subject s1 = new Subject();
		s1.setName("Introdução a Linguagem de Programação");
		s1.setCode("ILP");
		s1.setFrequency(1);
		s1.setHours(64);

		Subject s2 = new Subject();
		s2.setName("Banco de Dados 1");
		s2.setCode("BD1");
		s2.setFrequency(1);
		s2.setHours(82);

		Subject s3 = new Subject();
		s3.setName("Redes 1");
		s3.setCode("RD1");
		s3.setFrequency(1);
		s3.setHours(100);

		this.subjectRepository.saveAll(Arrays.asList(s1, s2, s3));

	}

	private String buildURI() {
		return "http://localhost:" + this.port + "/courses/";
	}

	@Test
	public void testListAllCourses() {
		ResponseEntity<Response<List<Course>>> courses = restTemplate
				.withBasicAuth("rasmoo", "$2a$10$RqGILYfa2BFYvw.bMtaMYOeZYYUY5NUOSTIrSb4AXt0rOoll9EQwS")
				.exchange(buildURI(), HttpMethod.GET, null, new ParameterizedTypeReference<Response<List<Course>>>() {
				});

		assertNotNull(courses.getBody().getData());
		assertEquals(1, courses.getBody().getData().size());
		assertEquals(200, courses.getBody().getStatusCode());
	}

	@Test
	public void testCreateCourse() {
		CourseDto courseDto = CourseDataFactory.buildDtoToCreate();

		HttpEntity<CourseDto> request = new HttpEntity<>(courseDto);

		ResponseEntity<Response<Boolean>> response = restTemplate.withBasicAuth("rasmoo", "rasmoo123")
				.exchange(buildURI(), HttpMethod.POST, request, new ParameterizedTypeReference<Response<Boolean>>() {
				});

		List<Course> coursesList = this.courseRepository.findAll();

		assertNotNull(response.getBody().getData());
		assertEquals(2, coursesList.size());
		assertEquals(201, response.getBody().getStatusCode());
	}

	@Test
	public void testFindCourseById() {
		List<Course> courseList = this.courseRepository.findAll();
		Long id = courseList.get(0).getId();

		ResponseEntity<Response<Course>> course = restTemplate.withBasicAuth("rasmoo", "rasmoo123").exchange(
				"http://localhost:" + this.port + "/courses/" + id, HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<Course>>() {
				});

		assertNotNull(course.getBody().getData());
		assertEquals("EC", course.getBody().getData().getCode());
		assertEquals(200, course.getBody().getStatusCode());
	}

	@Test
	public void testFindCourseByCode() {
		ResponseEntity<Response<Course>> course = restTemplate.withBasicAuth("rasmoo", "rasmoo123").exchange(
				"http://localhost:" + this.port + "/courses/code/EC", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<Course>>() {
				});

		assertNotNull(course.getBody().getData());
		assertEquals("EC", course.getBody().getData().getCode());
		assertEquals(200, course.getBody().getStatusCode());
	}

	@Test
	public void testUpdateCourse() {
		List<Course> courses = this.courseRepository.findAll();
		Course course = courses.get(0);

		CourseDto courseDto = new CourseDto();
		courseDto.setId(course.getId());
		courseDto.setCode(course.getCode());
		courseDto.setName("Test Course Updated");
		courseDto.setSubjects(Arrays.asList(1L));

		HttpEntity<CourseDto> request = new HttpEntity<>(courseDto);

		ResponseEntity<Response<Boolean>> response = restTemplate.withBasicAuth("rasmoo", "rasmoo123")
				.exchange(buildURI(), HttpMethod.PUT, request, new ParameterizedTypeReference<Response<Boolean>>() {
				});

		Optional<Course> courseUpdated = this.courseRepository.findById(course.getId());

		assertNotNull(response.getBody().getData());
		assertEquals("Test Course Updated", courseUpdated.get().getName());
		assertEquals(200, response.getBody().getStatusCode());
	}

	@Test
	public void testDeleteCourse() {
		List<Course> listCourses = this.courseRepository.findAll();
		Long id = listCourses.get(0).getId();

		ResponseEntity<Response<Boolean>> response = restTemplate.withBasicAuth("rasmoo", "rasmoo123").exchange(
				"http://localhost:" + this.port + "/courses/" + id, HttpMethod.DELETE, null,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});

		List<Course> coursesList = this.courseRepository.findAll();

		assertNotNull(response.getBody().getData());
		assertEquals(0, coursesList.size());
		assertEquals(200, response.getBody().getStatusCode());
	}

}
