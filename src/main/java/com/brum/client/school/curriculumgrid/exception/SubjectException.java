package com.brum.client.school.curriculumgrid.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class SubjectException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final HttpStatus httpStatus;
	
	public SubjectException(final String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}
}
