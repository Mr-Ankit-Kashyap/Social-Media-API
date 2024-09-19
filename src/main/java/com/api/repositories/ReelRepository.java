package com.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.entities.Reel;

public interface ReelRepository extends JpaRepository<Reel, Long> {

	@Query("SELECT r FROM Reel r WHERE r.user.username=:username")
	List<Reel> findReelsByUsername(String username);

}
