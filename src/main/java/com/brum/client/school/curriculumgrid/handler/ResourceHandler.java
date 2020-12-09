package com.brum.client.school.curriculumgrid.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.brum.client.school.curriculumgrid.exception.SubjectException;
import com.brum.client.school.curriculumgrid.model.ErrorMapResponse;
import com.brum.client.school.curriculumgrid.model.ErrorMapResponse.ErrorMapResponseBuilder;
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

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMapResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException m) {
		
		Map<String, String> erros = new HashMap<>();
		
		m.getBindingResult().getAllErrors().forEach(erro -> {
			String campo = ((FieldError)erro).getField();
			String mensagem = erro.getDefaultMessage();
			erros.put(campo, mensagem);
		});
		
		ErrorMapResponseBuilder errorMap = ErrorMapResponse.builder();
		errorMap.erros(erros).httpStatus(HttpStatus.BAD_REQUEST.value()).timeStamp(System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap.build());
	}
}
