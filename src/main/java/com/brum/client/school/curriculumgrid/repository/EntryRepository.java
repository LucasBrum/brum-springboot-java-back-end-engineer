package com.brum.client.school.curriculumgrid.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.brum.client.school.curriculumgrid.entity.Entry;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long>{

	@Query("SELECT e FROM Entry e where e.user.id = :userId")
	public List<Entry> findAllByUserId(@Param("userId") Long userId);
}
