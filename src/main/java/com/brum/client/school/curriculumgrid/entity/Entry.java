package com.brum.client.school.curriculumgrid.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.brum.client.financescontroll.enums.TypeEnum;

import lombok.Data;

@Data
@Entity
public class Entry implements Serializable{
	
	private static final long serialVersionUID = -6915675231972324665L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "type")
	private TypeEnum tipo;
	
	@Column(name = "date")
	private Date data = new Date();
	
	@Column(name = "value")
	private int valor;
	
	@OneToOne(cascade = CascadeType.REFRESH)
	private Category categoria = new Category();
}
