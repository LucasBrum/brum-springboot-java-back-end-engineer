package com.brum.client.school.curriculumgrid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brum.client.school.curriculumgrid.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
