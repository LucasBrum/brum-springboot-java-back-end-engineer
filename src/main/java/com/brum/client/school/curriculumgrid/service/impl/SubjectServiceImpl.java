package com.brum.client.school.curriculumgrid.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brum.client.school.curriculumgrid.entity.Subject;
import com.brum.client.school.curriculumgrid.repository.SubjectRepository;
import com.brum.client.school.curriculumgrid.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService{

	@Autowired		
	private SubjectRepository subjectRepository;
	
	@Override
	public Subject update(Subject subject) {
		
		try {
			Subject subjectUpdated = this.subjectRepository.findById(subject.getId()).get();
			
			subjectUpdated.setName(subject.getName());
			subjectUpdated.setCode(subject.getCode());
			subjectUpdated.setHours(subject.getHours());
			subjectUpdated.setFrequency(subject.getFrequency());
			
			this.subjectRepository.save(subjectUpdated);
			
			return subjectUpdated;
		} catch (Exception e) {
			return null;
		}
		
	}

	@Override
	public Boolean delete(Long id) {
		try {
			this.subjectRepository.deleteById(id);
			return true;
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
			return null;
			
		} catch (Exception e) {
			return null;
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
