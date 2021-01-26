package com.brum.client.school.curriculumgrid.test.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.brum.client.school.curriculumgrid.dto.CourseDto;
import com.brum.client.school.curriculumgrid.entity.Course;
import com.brum.client.school.curriculumgrid.model.Response;
import com.brum.client.school.curriculumgrid.service.CourseService;
import com.brum.client.school.curriculumgrid.test.datafactory.CourseDataFactory;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseControllerUnitTest {

	@LocalServerPort
	private int port;

	@MockBean
	private CourseService courseService;

	@Autowired
	private TestRestTemplate restTemplate;

	private static CourseDto courseDto;
	private static Course course;

	@BeforeAll
	public static void init() {
		courseDto = CourseDataFactory.buildDto();
		course = CourseDataFactory.build();
	}

	@Test
	public void testListAll() {
		Mockito.when(this.courseService.listAll()).thenReturn(new ArrayList<Course>());

		ResponseEntity<Response<List<Course>>> courses = restTemplate.exchange(
				"http://localhost:" + this.port + "/courses/", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<Course>>>() {
				});

		assertNotNull(courses.getBody().getData());
		assertEquals(200, courses.getBody().getStatusCode());

	}

	@Test
	public void testCreate() {
		Mockito.when(this.courseService.create(courseDto)).thenReturn(Boolean.TRUE);

		HttpEntity<CourseDto> request = new HttpEntity<>(courseDto);

		ResponseEntity<Response<Boolean>> course = restTemplate.exchange("http://localhost:" + this.port + "/courses/",
				HttpMethod.POST, request, new ParameterizedTypeReference<Response<Boolean>>() {
				});

		assertNotNull(course.getBody().getData());
		assertEquals(200, course.getBody().getStatusCode());
	}

	@Test
	public void testFindById() {
		Mockito.when(this.courseService.findById(1L)).thenReturn(course);

		ResponseEntity<Response<CourseDto>> course = restTemplate.exchange(
				"http://localhost:" + this.port + "/courses/1", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<CourseDto>>() {
				});

		assertNotNull(course.getBody().getData());
		assertEquals(200, course.getBody().getStatusCode());
	}

	@Test
	public void testFindByCode() {
		Mockito.when(this.courseService.findByCode("BD")).thenReturn(course);

		ResponseEntity<Response<CourseDto>> course = restTemplate.exchange(
				"http://localhost:" + this.port + "/courses/code/BD", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<CourseDto>>() {
				});

		assertNotNull(course.getBody().getData());
		assertEquals(200, course.getBody().getStatusCode());
	}

	@Test
	public void testUpdateCourse() {
		Mockito.when(this.courseService.update(courseDto)).thenReturn(Boolean.TRUE);

		HttpEntity<CourseDto> request = new HttpEntity<>(courseDto);

		ResponseEntity<Response<Boolean>> course = restTemplate.exchange("http://localhost:" + this.port + "/courses/",
				HttpMethod.PUT, request, new ParameterizedTypeReference<Response<Boolean>>() {
				});

		assertNotNull(course.getBody().getData());
		assertEquals(200, course.getBody().getStatusCode());

	}

	@Test
	public void testDeleteCourse() {
		Mockito.when(this.courseService.delete(1L)).thenReturn(Boolean.TRUE);

		ResponseEntity<Response<Boolean>> course = restTemplate.exchange("http://localhost:" + this.port + "/courses/1",
				HttpMethod.DELETE, null, new ParameterizedTypeReference<Response<Boolean>>() {
				});

		assertNotNull(course.getBody().getData());
		assertEquals(200, course.getBody().getStatusCode());

	}

}
