package com.brum.client.school.curriculumgrid.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CursoDto {
	
	@Size(min = 10, max = 30)
	@NotBlank(message = "Enter the Course name")
	private String name;
	
	@Size(min = 2, max = 5)
	@NotBlank(message = "Enter the Course code")
	private String courseCode;
	
	private List<Long> subjects;
}
