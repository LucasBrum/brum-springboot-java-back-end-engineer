package com.brum.client.school.curriculumgrid.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import com.brum.client.school.curriculumgrid.enums.TypeEnum;

import lombok.Data;

@Data
@Entity
public class Entry implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	
	private TypeEnum type;
	
	private Date date = new Date();
	
	private int value;
	
	@OneToOne(cascade = CascadeType.REFRESH)
	private Category category = new Category();
}
