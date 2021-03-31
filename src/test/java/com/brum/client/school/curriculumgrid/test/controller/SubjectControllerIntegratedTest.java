package com.brum.client.school.curriculumgrid.test.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

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

import com.brum.client.school.curriculumgrid.dto.SubjectDto;
import com.brum.client.school.curriculumgrid.entity.Subject;
import com.brum.client.school.curriculumgrid.model.Response;
import com.brum.client.school.curriculumgrid.repository.SubjectRepository;
import com.brum.client.school.curriculumgrid.test.datafactory.SubjectDataFactory;

@RunWith(JUnitPlatform.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SubjectControllerIntegratedTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private SubjectRepository subjectRepository;

	@BeforeEach
	public void init() {
		this.buildDataBase();
	}

	@AfterEach
	public void finish() {
		this.subjectRepository.deleteAll();
	}

	private void buildDataBase() {
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
		return "http://localhost:" + this.port + "/subjects/";
	}

	@Test
	public void testListAllSubjects() {
		ResponseEntity<Response<List<SubjectDto>>> subjects = restTemplate.withBasicAuth("rasmoo", "rasmoo123")
				.exchange(buildURI(), HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<SubjectDto>>>() {
				});

		assertNotNull(subjects.getBody().getData());
		assertEquals(3, subjects.getBody().getData().size());
		assertEquals(200, subjects.getBody().getStatusCode());

	}

	@Test
	public void testFindSubjectById() {

		List<Subject> subjectList = this.subjectRepository.findAll();
		Long id = subjectList.get(0).getId();

		ResponseEntity<Response<SubjectDto>> subject = restTemplate.withBasicAuth("rasmoo", "rasmoo123").exchange(
				"http://localhost:" + this.port + "/subjects/" + id, HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<SubjectDto>>() {
				});

		assertNotNull(subject.getBody().getData());
		assertEquals("ILP", subject.getBody().getData().getCode());
		assertEquals(200, subject.getBody().getStatusCode());
	}

	@Test
	public void testFindSubjectByMinHour() {
		ResponseEntity<Response<List<SubjectDto>>> subject = restTemplate.withBasicAuth("rasmoo", "rasmoo123").exchange(
				"Http://localhost:" + this.port + "/subjects/min-hour/80", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<SubjectDto>>>() {
				});

		assertNotNull(subject.getBody().getData());
		assertEquals(2, subject.getBody().getData().size());
		assertEquals(200, subject.getBody().getStatusCode());
	}

	@Test
	public void testFindSubjectByFrequency() {
		ResponseEntity<Response<List<SubjectDto>>> subject = restTemplate.withBasicAuth("rasmoo", "rasmoo123").exchange(
				"Http://localhost:" + this.port + "/subjects/min-frequency/1", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<SubjectDto>>>() {
				});

		assertNotNull(subject.getBody().getData());
		assertEquals(3, subject.getBody().getData().size());
		assertEquals(200, subject.getBody().getStatusCode());
	}

	@Test
	public void testUpdateSubject() {

		List<Subject> listSubjects = this.subjectRepository.findAll();
		Subject subject = listSubjects.get(0);

		subject.setName("Test Update Subject");

		HttpEntity<Subject> subjectRequest = new HttpEntity<>(subject);

		ResponseEntity<Response<Boolean>> subjects = restTemplate.withBasicAuth("rasmoo", "rasmoo123").exchange(
				"Http://localhost:" + this.port + "/subjects/", HttpMethod.PUT, subjectRequest,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});

		Subject subjectUpdated = this.subjectRepository.findById(subject.getId()).get();

		assertNotNull(subjects.getBody().getData());
		assertEquals("Test Update Subject", subjectUpdated.getName());
		assertEquals(200, subjects.getBody().getStatusCode());
	}

	@Test
	public void testDeleteSubject() {
		List<Subject> listSubjects = this.subjectRepository.findAll();
		Long id = listSubjects.get(0).getId();

		ResponseEntity<Response<Boolean>> subject = restTemplate.withBasicAuth("rasmoo", "rasmoo123").exchange(
				"Http://localhost:" + this.port + "/subjects/" + id, HttpMethod.DELETE, null,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});

		List<Subject> listSubjectsUpdated = this.subjectRepository.findAll();

		assertNotNull(subject.getBody().getData());
		assertEquals(2, listSubjectsUpdated.size());
		assertEquals(200, subject.getBody().getStatusCode());
	}

	@Test
	public void testCreateSubject() {
		Subject subject = SubjectDataFactory.buildToCreate();

		HttpEntity<Subject> request = new HttpEntity<>(subject);

		ResponseEntity<Response<Boolean>> subjectResponse = restTemplate.withBasicAuth("rasmoo", "rasmoo123").exchange(
				"Http://localhost:" + this.port + "/subjects/", HttpMethod.POST, request,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});

		List<Subject> listSubjectsUpdated = this.subjectRepository.findAll();

		assertNotNull(subjectResponse.getBody().getData());
		assertEquals(4, listSubjectsUpdated.size());
		assertEquals(201, subjectResponse.getBody().getStatusCode());
	}

}
