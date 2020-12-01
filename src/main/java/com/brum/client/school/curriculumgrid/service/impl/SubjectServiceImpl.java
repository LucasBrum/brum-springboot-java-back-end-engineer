package com.brum.client.school.curriculumgrid.service.impl;

import java.util.ArrayList;
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
public class SubjectServiceImpl implements SubjectService{

	@Autowired		
	private SubjectRepository subjectRepository;
	
	@Override
	public Boolean update(SubjectDto subjectDto) {
		
		try {
			
			this.findById(subjectDto.getId());
			
			ModelMapper mapper = new ModelMapper();
			Subject subjectUpdated = mapper.map(subjectDto, Subject.class);
			
			this.subjectRepository.save(subjectUpdated);
			
			return true;
			
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
			return true;
		} catch (SubjectException subjectException) {
			throw subjectException;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean create(SubjectDto subjectDto) {
		try {
			
			ModelMapper mapper = new ModelMapper();
			Subject subject = mapper.map(subjectDto, Subject.class);
			
			this.subjectRepository.save(subject);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public SubjectDto findById(Long id) {
		try {
			
			ModelMapper mapper = new ModelMapper();
			Optional<Subject> subject = subjectRepository.findById(id);
			
			if(subject.isPresent()) {
				
				return mapper.map(subject.get(), SubjectDto.class);
				
			}
			
			throw new SubjectException("Subject Not Found.", HttpStatus.NOT_FOUND);
			
		} catch (SubjectException subjectException) {
			throw subjectException;
		} catch (Exception e) {
			throw new SubjectException("Internal error identified. Please contact customer support.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<SubjectDto> listAll() {
		try {
			ModelMapper mapper = new ModelMapper();
			
			List<Subject> subjectList = this.subjectRepository.findAll();
			
			return mapper.map(subjectList,new TypeToken<List<SubjectDto>>() {}.getType());
			
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

}
