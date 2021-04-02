package com.brum.client.school.curriculumgrid.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class Category implements Serializable{
	private static final long serialVersionUID = 4167460889626285518L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	
	@Column(name = "name")
	private String nome;
	
	@Column(name = "desciption")
	private String descricao;
}
