package com.brum.client.financescontroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brum.client.financescontroll.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
