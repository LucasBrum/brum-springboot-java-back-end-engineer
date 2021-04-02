package com.brum.client.financescontroll.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Category implements Serializable{
	private static final long serialVersionUID = 4167460889626285518L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name")
	private String nome;
	
	@Column(name = "desciption")
	private String descricao;
}
