package com.brum.client.school.curriculumgrid.constant;

import lombok.Getter;

@Getter
public enum HyperLinkConstant {

	UPDATE("UPDATE"),
	DELETE("DELETE"),
	LIST("GET_ALL"),
	GET("GET");
	
	private final String value;
	
	private HyperLinkConstant(String value) {
		this.value = value;
	}
}

