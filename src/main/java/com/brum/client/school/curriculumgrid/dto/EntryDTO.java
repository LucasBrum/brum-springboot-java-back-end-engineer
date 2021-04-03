package com.brum.client.school.curriculumgrid.dto;

import java.util.Date;

import com.brum.client.school.curriculumgrid.enums.TypeEnum;

import lombok.Data;

@Data
public class EntryDTO {
	private Long id;

	private TypeEnum type;

	private Date date = new Date();

	private int value;

	private Long categoryId;
}
