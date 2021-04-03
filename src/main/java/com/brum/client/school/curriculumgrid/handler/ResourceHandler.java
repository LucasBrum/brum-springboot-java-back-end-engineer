package com.brum.client.school.curriculumgrid.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.brum.client.school.curriculumgrid.exception.SubjectException;
import com.brum.client.school.curriculumgrid.model.Response;

@ControllerAdvice
public class ResourceHandler {

	@ExceptionHandler(SubjectException.class)
	public ResponseEntity<Response<String>> handlerSubjectException(SubjectException subjectException) {
		
		Response<String> response = new Response<>();
		response.setStatusCode(subjectException.getHttpStatus().value());
		response.setData(subjectException.getMessage());
		
		return ResponseEntity.status(subjectException.getHttpStatus()).body(response);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response<Map<String, String>>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException m) {
		
		Map<String, String> erros = new HashMap<>();
		
		m.getBindingResult().getAllErrors().forEach(erro -> {
			String campo = ((FieldError)erro).getField();
			String mensagem = erro.getDefaultMessage();
			erros.put(campo, mensagem);
		});
		
		Response<Map<String, String>> response = new Response<>();
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setData(erros);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<Response<String>> handlerUsernameNotFoundException(UsernameNotFoundException usernameNotFoundException) {
		Response<String> response = new Response<>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setData(usernameNotFoundException.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		
	}
}
