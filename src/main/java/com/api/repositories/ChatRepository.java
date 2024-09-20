package com.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.entities.Chat;
import com.api.entities.User;


public interface ChatRepository extends JpaRepository<Chat, Long> {

	@Query(value = "SELECT * FROM Chat WHERE username=:username " , nativeQuery = true)
	List<Chat> findChatsByUsername(@Param("username") String username);

	@Query("select c from Chat c Where :user Member of c.users And :reqUser Member of c.users")
	Chat findChatByUsername(@Param("user") User reqUsername, @Param("reqUser") User chatUsername);

}
