package com.brum.client.school.curriculumgrid.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.brum.client.school.curriculumgrid.enums.TypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_entry")
public class Entry implements Serializable{
	private static final long serialVersionUID = 2339286987100814160L;

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	
	@Column(name = "type")
	private TypeEnum type;
	
	@Column(name = "entry_date")
	private Date date = new Date();
	
	@Column(name = "entry_value")
	private int value;
	
	@OneToOne(cascade = CascadeType.REFRESH)
	private Category category = new Category();
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
}
