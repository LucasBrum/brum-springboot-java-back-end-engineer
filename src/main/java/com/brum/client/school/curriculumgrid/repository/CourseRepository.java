package com.brum.client.school.curriculumgrid.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.brum.client.school.curriculumgrid.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

	@Query("Select c from Course c where c.code = :code")
	public List<Course> findByCode(@Param("code") String code);
}
