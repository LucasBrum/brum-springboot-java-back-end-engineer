package com.brum.client.school.curriculumgrid.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.brum.client.school.curriculumgrid.dto.CourseDto;
import com.brum.client.school.curriculumgrid.entity.Course;
import com.brum.client.school.curriculumgrid.enums.ExceptionMessageEnum;
import com.brum.client.school.curriculumgrid.exception.CourseException;
import com.brum.client.school.curriculumgrid.repository.CourseRepository;
import com.brum.client.school.curriculumgrid.service.CourseService;

@Service
@CacheConfig(cacheNames = "course")
public class CourseServiceImpl implements CourseService {

	private ModelMapper mapper;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	public CourseServiceImpl(CourseRepository courseRepository) {
		this.mapper = new ModelMapper();
		this.courseRepository = courseRepository;
	}
	
	@Override
	public Boolean create(CourseDto courseDto) {
		try {
			Course course = this.mapper.map(courseDto, Course.class);
			
			this.courseRepository.save(course);
			
			return Boolean.TRUE;
		} catch (Exception e) {
			throw new CourseException(ExceptionMessageEnum.INTERNAL_ERROR.getValue(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean update(CourseDto course) {
		try {
			this.findById(course.getId());
			
			Course courseUpdated = this.mapper.map(course, Course.class);
			
			this.courseRepository.save(courseUpdated);
			
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
	public List<CourseDto> listAll() {
		try {
			List<Course> coursesList = this.courseRepository.findAll();
			
			List<CourseDto> courseDtosList = this.mapper.map(coursesList, new TypeToken<List<CourseDto>>()
					{}.getType());
			
			return courseDtosList;
		} catch (Exception e) {
			throw new CourseException(ExceptionMessageEnum.INTERNAL_ERROR.getValue(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	public List<CourseDto> findByCode(String code) {
		try {
			List<Course> courseList = this.courseRepository.findByCode(code);
			
			List<CourseDto> courseDtosList = this.mapper.map(courseList, new TypeToken<List<CourseDto>>()
					{}.getType());
			
			return courseDtosList;
		} catch (Exception e) {
			throw new CourseException(ExceptionMessageEnum.INTERNAL_ERROR.getValue(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@CachePut(key = "#id")
	public CourseDto findById(Long id) {
		try {
			Optional<Course> courseOptional = this.courseRepository.findById(id);
			
			if (courseOptional.isPresent()) {
				return this.mapper.map(courseOptional.get(), CourseDto.class);
			}
			
			throw new CourseException(ExceptionMessageEnum.COURSE_NOT_FOUND.getValue(), HttpStatus.NOT_FOUND);
			
		} catch (CourseException ce) {
			throw ce;
		} catch (Exception e) {
			throw e;
		}
	}

	
	
	

	
}
