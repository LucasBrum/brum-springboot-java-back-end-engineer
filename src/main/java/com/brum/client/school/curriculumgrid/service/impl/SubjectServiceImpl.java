package com.brum.client.school.curriculumgrid.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.brum.client.school.curriculumgrid.controller.SubjectController;
import com.brum.client.school.curriculumgrid.dto.SubjectDto;
import com.brum.client.school.curriculumgrid.entity.Subject;
import com.brum.client.school.curriculumgrid.exception.SubjectException;
import com.brum.client.school.curriculumgrid.repository.SubjectRepository;
import com.brum.client.school.curriculumgrid.service.SubjectService;

@CacheConfig(cacheNames = "subject")
@Service
public class SubjectServiceImpl implements SubjectService {
	private static final String MENSAGEM_ERRO = "Internal error identified. Please contact customer support.";
	private static final String SUBJECT_NOT_FOUND = "Subject Not Found.";
	private ModelMapper mapper;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	public SubjectServiceImpl(SubjectRepository subjectRepository) {
		this.mapper = new ModelMapper();
		this.subjectRepository = subjectRepository;
	}

	@Override
	public Boolean update(SubjectDto subject) {

		try {

			this.findById(subject.getId());

			Subject subjectUpdated = this.mapper.map(subject, Subject.class);

			this.subjectRepository.save(subjectUpdated);

			return Boolean.TRUE;

		} catch (SubjectException subjectException) {
			throw subjectException;
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public Boolean delete(Long id) {
		try {
			this.findById(id);
			this.subjectRepository.deleteById(id);
			return Boolean.TRUE;
		} catch (SubjectException subjectException) {
			throw subjectException;
		} catch (Exception e) {
			throw new SubjectException(MENSAGEM_ERRO,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean create(SubjectDto subjectDto) {
		try {

			Subject subject = this.mapper.map(subjectDto, Subject.class);

			this.subjectRepository.save(subject);

			return Boolean.TRUE;
		} catch (Exception e) {
			throw new SubjectException(MENSAGEM_ERRO,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	@CachePut(key = "#id")
	public SubjectDto findById(Long id) {
		try {

			Optional<Subject> subject = subjectRepository.findById(id);

			if (subject.isPresent()) {

				return this.mapper.map(subject.get(), SubjectDto.class);

			}

			throw new SubjectException(SUBJECT_NOT_FOUND, HttpStatus.NOT_FOUND);

		} catch (SubjectException subjectException) {
			throw subjectException;
		} catch (Exception e) {
			throw new SubjectException(MENSAGEM_ERRO,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CachePut(unless = "#result.size() < 3")
	@Override
	public List<SubjectDto> listAll() {
		try {
			List<Subject> subjectList = this.subjectRepository.findAll();

			List<SubjectDto> subjectDto = this.mapper.map(subjectList, new TypeToken<List<SubjectDto>>() {
			}.getType());

			subjectDto.forEach(subject -> 
				subject.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SubjectController.class).findById(subject.getId())).withSelfRel()));
			
			
			return subjectDto;

		} catch (Exception e) {
			throw new SubjectException(MENSAGEM_ERRO,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
