package com.brum.client.school.curriculumgrid.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.brum.client.school.curriculumgrid.dto.CourseDto;
import com.brum.client.school.curriculumgrid.entity.Course;
import com.brum.client.school.curriculumgrid.entity.Subject;
import com.brum.client.school.curriculumgrid.enums.ExceptionMessageEnum;
import com.brum.client.school.curriculumgrid.exception.CourseException;
import com.brum.client.school.curriculumgrid.repository.CourseRepository;
import com.brum.client.school.curriculumgrid.repository.SubjectRepository;
import com.brum.client.school.curriculumgrid.service.CourseService;

@Service
@CacheConfig(cacheNames = "course")
public class CourseServiceImpl implements CourseService {

	private ModelMapper mapper;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	public CourseServiceImpl(CourseRepository courseRepository) {
		this.mapper = new ModelMapper();
		this.courseRepository = courseRepository;
	}
	
	@Override
	public Boolean create(CourseDto courseDto) {
		try {
			List<Subject> subjectsList = new ArrayList<>();
			
			if (!courseDto.getSubjects().isEmpty()) {
				courseDto.getSubjects().forEach(subject -> {
					if(this.subjectRepository.findById(subject).isPresent())
						subjectsList.add(this.subjectRepository.findById(subject).get());
						
				});
			}
			
			Course course = this.mapper.map(courseDto, Course.class);
			course.setSubjects(subjectsList);
			this.courseRepository.save(course);
			
			return Boolean.TRUE;
		} catch (Exception e) {
			throw new CourseException(ExceptionMessageEnum.INTERNAL_ERROR.getValue(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean update(CourseDto course) {
		try {
			Course courseSaved = this.findById(course.getId());
			
			BeanUtils.copyProperties(course, courseSaved, "id");
			
			this.courseRepository.save(courseSaved);
			
			return Boolean.TRUE;
			
		} catch (CourseException ce) {
			throw ce;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Boolean delete(Long id) {
		try {
			this.findById(id);
			this.courseRepository.deleteById(id);
			return Boolean.TRUE;
			
		} catch (CourseException ce) {
			throw ce;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@CachePut(unless = "#result.size() < 3")
	public List<Course> listAll() {
		try {
			return this.courseRepository.findAll();
		} catch (Exception e) {
			throw new CourseException(ExceptionMessageEnum.INTERNAL_ERROR.getValue(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	public Course findByCode(String code) {
		try {
			
			return this.courseRepository.findByCode(code);
			
		} catch (Exception e) {
			throw new CourseException(ExceptionMessageEnum.INTERNAL_ERROR.getValue(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@CachePut(key = "#id")
	public Course findById(Long id) {
		try {
			Optional<Course> courseOptional = this.courseRepository.findById(id);
			
			if (courseOptional.isPresent()) {
				return courseOptional.get();
			}
			
			throw new CourseException(ExceptionMessageEnum.COURSE_NOT_FOUND.getValue(), HttpStatus.NOT_FOUND);
			
		} catch (CourseException ce) {
			throw ce;
		} catch (Exception e) {
			throw e;
		}
	}
	
}
