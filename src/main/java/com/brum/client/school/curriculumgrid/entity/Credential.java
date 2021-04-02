package com.brum.client.school.curriculumgrid.entity;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Credential {
	
	private String email;

	private String senha;
	
}
