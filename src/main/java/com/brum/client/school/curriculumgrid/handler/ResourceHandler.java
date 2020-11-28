package com.brum.client.school.curriculumgrid.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.brum.client.school.curriculumgrid.exception.SubjectException;
import com.brum.client.school.curriculumgrid.model.ErrorResponse;
import com.brum.client.school.curriculumgrid.model.ErrorResponse.ErrorResponseBuilder;

@ControllerAdvice
public class ResourceHandler {

	@ExceptionHandler(SubjectException.class)
	public ResponseEntity<ErrorResponse> handlerSubjectException(SubjectException subjectException) {
		ErrorResponseBuilder error = ErrorResponse.builder();
		error.httpStatus(subjectException.getHttpStatus().value());
		error.message(subjectException.getMessage());
		error.timeStamp(System.currentTimeMillis());
		return ResponseEntity.status(subjectException.getHttpStatus()).body(error.build());
	}
}
