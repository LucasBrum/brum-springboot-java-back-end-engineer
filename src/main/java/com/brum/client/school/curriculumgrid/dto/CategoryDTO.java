package com.brum.client.school.curriculumgrid.dto;

import com.brum.client.school.curriculumgrid.entity.User;

import lombok.Data;

@Data
public class CategoryDTO {

	private Long id;
	
	private String name;
	
	private String description;
	
	private User user;
}
