package com.gabriele.eventhub.repository;

import com.gabriele.eventhub.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByOrganizerId(Long organizerId);
    
    @Query(value = "SELECT * FROM events e WHERE " +
    	       "(CAST(:startDate AS TIMESTAMP) IS NULL OR e.start_date >= CAST(:startDate AS TIMESTAMP)) AND " +
    	       "(CAST(:endDate AS TIMESTAMP) IS NULL OR e.end_date <= CAST(:endDate AS TIMESTAMP)) AND " +
    	       "(CAST(:venueId AS BIGINT) IS NULL OR e.venue_id = CAST(:venueId AS BIGINT)) AND " +
    	       "(CAST(:organizerId AS BIGINT) IS NULL OR e.organizer_id = CAST(:organizerId AS BIGINT))",
    	       nativeQuery = true)
    	List<Event> findByFilters(
    	        @Param("startDate") LocalDateTime startDate,
    	        @Param("endDate") LocalDateTime endDate,
    	        @Param("venueId") Long venueId,
    	        @Param("organizerId") Long organizerId,
    	        @Param("tagId") Long tagId);}