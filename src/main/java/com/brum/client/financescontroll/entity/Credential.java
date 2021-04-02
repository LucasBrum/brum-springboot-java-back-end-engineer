package com.brum.client.financescontroll.entity;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Credential {
	
	private String email;

	private String senha;
	
}
