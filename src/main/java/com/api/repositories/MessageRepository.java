package com.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.entities.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

	List<Message> findByChatId(Long chatId);

}
