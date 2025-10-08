package com.matwhiz.qrinternlogger.repository;

import com.matwhiz.qrinternlogger.entity.Intern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InternRepository extends JpaRepository<Intern, String> {
    
    Optional<Intern> findByAuthCode(String authCode);
    
    Optional<Intern> findByEmail(String email);
    
    Optional<Intern> findByAadhaarNumber(String aadhaarNumber);
    
    boolean existsByAuthCode(String authCode);
    
    boolean existsByEmail(String email);
    
    boolean existsByAadhaarNumber(String aadhaarNumber);
}
