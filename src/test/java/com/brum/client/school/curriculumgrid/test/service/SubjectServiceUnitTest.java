package com.brum.client.school.curriculumgrid.test.service;

import static org.junit.Assert.assertNotNull;
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

import com.brum.client.school.curriculumgrid.dto.SubjectDto;
import com.brum.client.school.curriculumgrid.entity.Subject;
import com.brum.client.school.curriculumgrid.repository.SubjectRepository;
import com.brum.client.school.curriculumgrid.service.impl.SubjectServiceImpl;
import com.brum.client.school.curriculumgrid.test.datafactory.SubjectDataFactory;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class SubjectServiceUnitTest {

	
	@Mock
	private SubjectRepository subjectRepository;
	
	@InjectMocks
	private SubjectServiceImpl subjectService;
	
	private static Subject subject;
	
	@BeforeAll
	public static void init() {
		subject = SubjectDataFactory.build();

	}
	
	
	@Test
	public void testListAllSuccess() {
		List<Subject> subjectsList = new ArrayList<>();
		subjectsList.add(subject);
		
		Mockito.when(this.subjectRepository.findAll()).thenReturn(subjectsList);
		
		List<SubjectDto> subjectsDtoList = this.subjectService.listAll();
		
		assertNotNull(subjectsDtoList);
		assertEquals("ILP", subjectsDtoList.get(0).getCode());
		assertEquals(1, subjectsDtoList.get(0).getId());
		assertEquals("/subjects/1", subjectsDtoList.get(0).getLinks().getRequiredLink("self").getHref());
		assertEquals(1, subjectsDtoList.size());
		
		Mockito.verify(this.subjectRepository, times(1)).findAll();
	}
	
	@Test
	public void testFindByMinHourSuccess() {
		List<Subject> subjectsList = new ArrayList<>();
		subjectsList.add(subject);
		
		Mockito.when(this.subjectRepository.findByMinHour(64)).thenReturn(subjectsList);
		
		List<SubjectDto> subjectsDtoList = this.subjectService.findByMinHour(64);
		
		assertNotNull(subjectsDtoList);
		assertEquals("ILP", subjectsDtoList.get(0).getCode());
		assertEquals(1, subjectsDtoList.get(0).getId());
		assertEquals(1, subjectsDtoList.size());
		
		Mockito.verify(this.subjectRepository, times(1)).findByMinHour(64);
	}
	
	@Test
	public void testFindByFrequencySuccess() {
		List<Subject> subjectsList = new ArrayList<>();
		subjectsList.add(subject);
		
		Mockito.when(this.subjectRepository.findByFrequency(1)).thenReturn(subjectsList);
		
		List<SubjectDto> subjectsDtoList = this.subjectService.findByFrequency(1);
		
		assertNotNull(subjectsDtoList);
		assertEquals("ILP", subjectsDtoList.get(0).getCode());
		assertEquals(1, subjectsDtoList.get(0).getId());
		assertEquals(1, subjectsDtoList.size());
		
		Mockito.verify(this.subjectRepository, times(1)).findByFrequency(1);
	}
	
}
