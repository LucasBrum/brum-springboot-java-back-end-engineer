package com.brum.client.school.curriculumgrid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brum.client.school.curriculumgrid.entity.Entry;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long>{

}
