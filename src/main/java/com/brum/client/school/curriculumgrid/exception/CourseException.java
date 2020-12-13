package com.brum.client.school.curriculumgrid.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CourseException extends RuntimeException{

	private static final long serialVersionUID = -6914902016569853794L;
	
	private final HttpStatus httpStatus;
	
	public CourseException(final String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}
	
}
