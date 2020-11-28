package com.brum.client.school.curriculumgrid.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.brum.client.school.curriculumgrid.entity.Subject;
import com.brum.client.school.curriculumgrid.exception.SubjectException;
import com.brum.client.school.curriculumgrid.repository.SubjectRepository;
import com.brum.client.school.curriculumgrid.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService{

	@Autowired		
	private SubjectRepository subjectRepository;
	
	@Override
	public Subject update(Subject subject) {
		
		try {
			
			Subject subjectUpdated = this.findById(subject.getId());
			
			subjectUpdated.setName(subject.getName());
			subjectUpdated.setCode(subject.getCode());
			subjectUpdated.setHours(subject.getHours());
			subjectUpdated.setFrequency(subject.getFrequency());
			
			this.subjectRepository.save(subjectUpdated);
			
			return subjectUpdated;
			
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
	public Boolean create(Subject subject) {
		try {
			this.subjectRepository.save(subject);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Subject findById(Long id) {
		try {
			
			Optional<Subject> subject = subjectRepository.findById(id);
			
			if(subject.isPresent()) {
				return subject.get();
			}
			
			throw new SubjectException("Subject Not Found.", HttpStatus.NOT_FOUND);
			
		} catch (SubjectException subjectException) {
			throw subjectException;
		} catch (Exception e) {
			throw new SubjectException("Internal error identified. Please contact customer support.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<Subject> listAll() {
		try {
			return this.subjectRepository.findAll();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

}
