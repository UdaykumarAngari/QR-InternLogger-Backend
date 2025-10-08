package com.matwhiz.qrinternlogger.repository;

import com.matwhiz.qrinternlogger.entity.EntryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
// import java.time.LocalDate;
import java.util.List;

@Repository
public interface EntryLogRepository extends JpaRepository<EntryLog, Long> {
    
    List<EntryLog> findByInternIdOrderByTimestampDesc(String internId);
    
    List<EntryLog> findAllByOrderByTimestampDesc();
    
    @Query("SELECT e FROM EntryLog e WHERE e.timestamp >= :startDate AND e.timestamp <= :endDate ORDER BY e.timestamp DESC")
    List<EntryLog> findByTimestampBetween(@Param("startDate") LocalDateTime startDate, 
                                        @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(e) FROM EntryLog e WHERE e.internId = :internId AND e.timestamp >= :start AND e.timestamp < :end")
    long countEntriesByInternIdBetween(@Param("internId") String internId,
                                       @Param("start") LocalDateTime start,
                                       @Param("end") LocalDateTime end);
    
    @Query("SELECT e FROM EntryLog e WHERE e.internId = :internId AND e.timestamp >= :start AND e.timestamp < :end")
    List<EntryLog> findEntriesByInternIdBetween(@Param("internId") String internId,
                                                @Param("start") LocalDateTime start,
                                                @Param("end") LocalDateTime end);
}
