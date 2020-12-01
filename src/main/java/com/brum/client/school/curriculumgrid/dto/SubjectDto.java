package com.brum.client.school.curriculumgrid.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubjectDto {
	
	private Long id;
	
	@NotBlank(message = "Enter the subject name")
	private String name;
	
	@NotBlank(message = "Enter the subject code")
	private String code;
	
	@Min(value = 34, message = "34 hours minimum allowed")
	@Max(value = 120, message = "120 hours maximum allowed")
	private int hours;
	
	@Min(value = 1, message = "1 time minimum allowed in the year")
	@Max(value = 2, message = "2 times maximum allowed in the year")
	private int frequency;
}
