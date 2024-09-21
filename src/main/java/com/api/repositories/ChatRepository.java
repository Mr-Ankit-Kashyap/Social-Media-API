package com.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.entities.Chat;
import com.api.entities.User;

public interface ChatRepository extends JpaRepository<Chat, Long> {

	@Query("SELECT c FROM Chat c WHERE c.username = :username")
	List<Chat> findChatsByUsername(@Param("username") String username);

	@Query("select c from Chat c Where :user Member of c.users And :reqUser Member of c.users")
	Chat findChatByUser(@Param("user") User reqUser, @Param("reqUser") User chatUser);

}
