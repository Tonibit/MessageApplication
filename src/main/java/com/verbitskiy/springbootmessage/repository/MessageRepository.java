package com.verbitskiy.springbootmessage.repository;

import com.verbitskiy.springbootmessage.domain.Message;
import com.verbitskiy.springbootmessage.dto.MessageDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT * FROM messages WHERE person_id = :id ORDER BY id DESC LIMIT :limit",
            nativeQuery = true)
    List<Message> findByValue(@Param("id")Long personId, @Param("limit") int value);
}
