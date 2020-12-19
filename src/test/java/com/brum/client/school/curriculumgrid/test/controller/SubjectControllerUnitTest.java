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

import com.brum.client.school.curriculumgrid.dto.SubjectDto;
import com.brum.client.school.curriculumgrid.model.Response;
import com.brum.client.school.curriculumgrid.service.SubjectService;
import com.brum.client.school.curriculumgrid.test.datafactory.SubjectDataFactory;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class SubjectControllerUnitTest {

	@LocalServerPort
	private int port;

	@MockBean
	private SubjectService subjectService;

	@Autowired
	private TestRestTemplate restTemplate;

	private static SubjectDto subjectDto;

	@BeforeAll
	public static void init() {
		subjectDto = SubjectDataFactory.buildDto();

	}

	@Test
	public void testListAllSubjects() {
		Mockito.when(this.subjectService.listAll()).thenReturn(new ArrayList<SubjectDto>());

		ResponseEntity<Response<List<SubjectDto>>> subjects = restTemplate.exchange(
				"http://localhost:" + this.port + "/subjects/", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<SubjectDto>>>() {
				});

		assertNotNull(subjects.getBody().getData());
		assertEquals(200, subjects.getBody().getStatusCode());

	}

	@Test
	public void testCreateSubject() {
		Mockito.when(this.subjectService.create(subjectDto)).thenReturn(Boolean.TRUE);

		HttpEntity<SubjectDto> request = new HttpEntity<>(subjectDto);

		ResponseEntity<Response<Boolean>> subject = restTemplate.exchange(
				"Http://localhost:" + this.port + "/subjects/", HttpMethod.POST, request,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});

		assertNotNull(subject.getBody().getData());
		assertEquals(201, subject.getBody().getStatusCode());
	}

	@Test
	public void testFindSubjectById() {
		Mockito.when(this.subjectService.findById(1L)).thenReturn(subjectDto);

		ResponseEntity<Response<SubjectDto>> subject = restTemplate.exchange(
				"Http://localhost:" + this.port + "/subjects/1", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<SubjectDto>>() {
				});

		assertNotNull(subject.getBody().getData());
		assertEquals(200, subject.getBody().getStatusCode());
	}

	@Test
	public void testUpdateSubject() {
		Mockito.when(this.subjectService.update(subjectDto)).thenReturn(Boolean.TRUE);

		HttpEntity<SubjectDto> subjectRequest = new HttpEntity<>(subjectDto);

		ResponseEntity<Response<Boolean>> subject = restTemplate.exchange(
				"Http://localhost:" + this.port + "/subjects/", HttpMethod.PUT, subjectRequest,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});

		assertNotNull(subject.getBody().getData());
		assertEquals(200, subject.getBody().getStatusCode());
	}

	@Test
	public void testDeleteSubject() {
		Mockito.when(this.subjectService.delete(1L)).thenReturn(Boolean.TRUE);

		ResponseEntity<Response<Boolean>> subject = restTemplate.exchange(
				"Http://localhost:" + this.port + "/subjects/1", HttpMethod.DELETE, null,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});

		assertNotNull(subject.getBody().getData());
		assertEquals(200, subject.getBody().getStatusCode());
	}

	@Test
	public void testFindSubjectByMinHour() {
		Mockito.when(this.subjectService.findByMinHour(50)).thenReturn(new ArrayList<SubjectDto>());

		ResponseEntity<Response<List<SubjectDto>>> subject = restTemplate.exchange(
				"Http://localhost:" + this.port + "/subjects/min-hour/40", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<SubjectDto>>>() {
				});

		assertNotNull(subject.getBody().getData());
		assertEquals(200, subject.getBody().getStatusCode());
	}

	@Test
	public void testFindSubjectByFrequency() {
		Mockito.when(this.subjectService.findByFrequency(1)).thenReturn(new ArrayList<SubjectDto>());

		ResponseEntity<Response<List<SubjectDto>>> subject = restTemplate.exchange(
				"Http://localhost:" + this.port + "/subjects/min-frequency/49", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<SubjectDto>>>() {
				});

		assertNotNull(subject.getBody().getData());
		assertEquals(200, subject.getBody().getStatusCode());
	}

}
