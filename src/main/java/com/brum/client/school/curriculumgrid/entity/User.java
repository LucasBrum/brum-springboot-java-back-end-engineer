package com.brum.client.school.curriculumgrid.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tb_user")
public class User implements Serializable{
	
	private static final long serialVersionUID = 4947066108504508475L;

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	
	@Column(name = "name")
	private String name;

	@JsonIgnore
	@Column(name = "credential")
	private Credential credential = new Credential();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@Column(name = "entry")
	private List<Entry> entries;

}
