package com.gabriele.eventhub.repository;

import com.gabriele.eventhub.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByEventId(Long eventId);

    boolean existsByUserIdAndEventId(Long userId, Long eventId);
}