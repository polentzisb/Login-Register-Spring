package com.polentzi.logreg.repositories;

import org.springframework.data.repository.CrudRepository;

import com.polentzi.logreg.models.User;

public interface UserRepo extends CrudRepository<User, Long>{
	 User findByEmail(String email);
}