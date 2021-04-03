package com.brum.client.school.curriculumgrid.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Credential implements Serializable {
	
	private static final long serialVersionUID = -606007934251623563L;

	private String email;

	private String password;
	
}
