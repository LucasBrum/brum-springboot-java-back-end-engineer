package com.brum.client.school.curriculumgrid.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tb_subject")
public class Subject implements Serializable {
	
	private static final long serialVersionUID = -1085127535992139037L;

	@Id
	@JsonInclude(Include.NON_NULL)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@JsonInclude(Include.NON_EMPTY)
	@Column(name = "name")
	private String name;
	
	@Column(name = "hours")
	private int hours;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "frequency")
	private int frequency;
	
	
}
