package com.brum.client.school.curriculumgrid.test.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.brum.client.school.curriculumgrid.dto.SubjectDto;
import com.brum.client.school.curriculumgrid.entity.Subject;
import com.brum.client.school.curriculumgrid.exception.SubjectException;
import com.brum.client.school.curriculumgrid.repository.SubjectRepository;
import com.brum.client.school.curriculumgrid.service.impl.SubjectServiceImpl;
import com.brum.client.school.curriculumgrid.test.datafactory.SubjectDataFactory;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
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
	
	@Test
	public void testFindByIdSucess() {
		Mockito.when(this.subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
		SubjectDto subjectDto = this.subjectService.findById(1L);
		
		assertNotNull(subjectDto);
		assertEquals("ILP", subjectDto.getCode());
		assertEquals(1, subjectDto.getId());
		assertEquals(1, subjectDto.getFrequency());
		
		Mockito.verify(this.subjectRepository, times(1)).findById(1L);
		
	}
	
	@Test
	public void testCreateSucess() {
		
		SubjectDto subjectDto = SubjectDataFactory.buildDtoToCreate();
		Subject subject = SubjectDataFactory.buildToCreate();

		lenient().when(this.subjectRepository.save(subject)).thenReturn(subject);
		
		Boolean isSubjectSaved = this.subjectService.create(subjectDto);
		
		assertTrue(isSubjectSaved);
		
		
	}
	
	@Test
	public void testUpdateSucess() {
		
		SubjectDto subjectDto = SubjectDataFactory.buildDto();

		lenient().when(this.subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
		lenient().when(this.subjectRepository.save(subject)).thenReturn(subject);
		
		Boolean isSubjectUpdated = this.subjectService.update(subjectDto);
		
		assertTrue(isSubjectUpdated);
		
	}
	
	@Test
	public void testDeleteSucess() {
		
		lenient().when(this.subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
		
		Boolean isSubjectDeleted = this.subjectService.delete(1L);
		
		assertTrue(isSubjectDeleted);
		
	}
	
	@Test
	public void testUpdateThrowSubjectException() {
		
		SubjectDto subjectDto = SubjectDataFactory.buildDto();

		lenient().when(this.subjectRepository.findById(1L)).thenReturn(Optional.empty());
		
		SubjectException subjectException;
		
		subjectException = assertThrows(SubjectException.class, () -> {
			this.subjectService.update(subjectDto);
		});
		
		assertEquals(HttpStatus.NOT_FOUND, subjectException.getHttpStatus());
		
		Mockito.verify(this.subjectRepository, times(1)).findById(1L);
		Mockito.verify(this.subjectRepository, times(0)).save(subject);
		
	}
	
	@Test
	public void testCreateThrowSubjectException() {
		
		SubjectDto subjectDto = SubjectDataFactory.buildDto();
		
		lenient().when(this.subjectRepository.findById(1L)).thenReturn(Optional.empty());
		
		SubjectException subjectException;
		
		subjectException = assertThrows(SubjectException.class, () -> {
			this.subjectService.create(subjectDto);
		});
		
		assertEquals(HttpStatus.BAD_REQUEST, subjectException.getHttpStatus());
		
		Mockito.verify(this.subjectRepository, times(0)).save(subject);
		
	}
	
	@Test
	public void testDeleteThrowSubjectException() {
		
		lenient().when(this.subjectRepository.findById(1L)).thenReturn(Optional.empty());
		
		SubjectException subjectException;
		
		subjectException = assertThrows(SubjectException.class, () -> {
			this.subjectService.delete(1L);
		});
		
		assertEquals(HttpStatus.NOT_FOUND, subjectException.getHttpStatus());
		
		Mockito.verify(this.subjectRepository, times(1)).findById(1L);
		Mockito.verify(this.subjectRepository, times(0)).delete(subject);
		
	}
	
	
}
