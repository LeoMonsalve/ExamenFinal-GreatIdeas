package com.leonel.beltExamen.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.leonel.beltExamen.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	 User findByEmail(String email);
	 User findByName (String name);
}
