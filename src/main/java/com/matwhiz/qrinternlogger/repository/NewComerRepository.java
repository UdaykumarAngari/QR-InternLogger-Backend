package com.matwhiz.qrinternlogger.repository;

import com.matwhiz.qrinternlogger.entity.NewComer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewComerRepository extends JpaRepository<NewComer, Long> {
    
    List<NewComer> findAllByOrderByEntryTimeDesc();
    
    @Query("SELECT n FROM NewComer n WHERE n.entryTime >= :startDate AND n.entryTime <= :endDate ORDER BY n.entryTime DESC")
    List<NewComer> findByEntryTimeBetween(@Param("startDate") LocalDateTime startDate, 
                                        @Param("endDate") LocalDateTime endDate);
    
    Optional<NewComer> findByVisitorId(String visitorId);
    
    boolean existsByVisitorId(String visitorId);
}
