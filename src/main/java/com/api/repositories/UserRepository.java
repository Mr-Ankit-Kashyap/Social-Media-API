package com.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.entities.User;


public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findByEmail(String email);
			
	//Custom query method for searching users by name or email
	@Query("SELECT u FROM User u WHERE u.name LIKE %:query% OR u.email LIKE %:query%")
    List<User> searchUser(@Param("query") String query);

	
}
