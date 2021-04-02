package com.brum.client.school.curriculumgrid.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class User implements Serializable{
	private static final long serialVersionUID = -6227685015951211718L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String nome;

	@JsonIgnore
	@Column(name = "credential")
	private Credential credencial = new Credential();

	@ManyToMany
	@JoinTable(name = "user_has_entries", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "entry_id") })
	@Column(name = "entry")
	private List<Entry> lancamento;

}
