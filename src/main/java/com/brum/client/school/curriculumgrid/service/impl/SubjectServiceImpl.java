package com.brum.client.school.curriculumgrid.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.brum.client.school.curriculumgrid.dto.SubjectDto;
import com.brum.client.school.curriculumgrid.entity.Subject;
import com.brum.client.school.curriculumgrid.exception.SubjectException;
import com.brum.client.school.curriculumgrid.repository.SubjectRepository;
import com.brum.client.school.curriculumgrid.service.SubjectService;

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
	public Boolean update(SubjectDto subjectDto) {

		try {

			this.findById(subjectDto.getId());

			Subject subjectUpdated = this.mapper.map(subjectDto, Subject.class);

			this.subjectRepository.save(subjectUpdated);

			return Boolean.TRUE;

		} catch (SubjectException subjectException) {
			throw subjectException;
		} catch (Exception e) {
			return null;
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

	@Override
	public List<SubjectDto> listAll() {
		try {
			List<Subject> subjectList = this.subjectRepository.findAll();

			return this.mapper.map(subjectList, new TypeToken<List<SubjectDto>>() {
			}.getType());

		} catch (Exception e) {
			throw new SubjectException(MENSAGEM_ERRO,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
