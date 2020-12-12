package com.brum.client.school.curriculumgrid.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.brum.client.school.curriculumgrid.entity.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>{

	@Query("Select s from Subject s where s.hours >= :minHour")
	public List<Subject> findByMinHour(@Param("minHour") int minHour);
	
	@Query("Select s from Subject s where s.frequency = :frequency")
	public List<Subject> findByFrequencyEqualOne(@Param("frequency") int frequency);
}
