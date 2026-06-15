package com.gabriele.eventhub.repository;

import com.gabriele.eventhub.entity.Ticket;
import com.gabriele.eventhub.entity.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    long countByEventIdAndStatus(Long eventId, TicketStatus status);

    List<Ticket> findByUserId(Long userId);
    
    boolean existsByUserIdAndEventIdAndStatus(Long userId, Long eventId, TicketStatus status);
}