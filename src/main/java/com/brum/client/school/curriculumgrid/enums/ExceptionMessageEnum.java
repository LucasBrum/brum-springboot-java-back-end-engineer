package com.brum.client.school.curriculumgrid.enums;

import lombok.Getter;

@Getter
public enum ExceptionMessageEnum {

	INTERNAL_ERROR("Internal error identified. Please contact customer support."),
	SUBJECT_NOT_FOUND("Subject Not Found."),
	COURSE_NOT_FOUND("Course Not Found"),
	ERROR_ID_INFORMED("Error! Id Informed."),
	USERNAME_NOTFOUND("Username Not Found");
	
	private final String value;
	
	private ExceptionMessageEnum(String value) {
		this.value = value;
	}
}
