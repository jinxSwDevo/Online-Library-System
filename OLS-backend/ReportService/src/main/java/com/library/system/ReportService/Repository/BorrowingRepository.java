package com.library.system.ReportService.Repository;
 
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.library.system.ReportService.entity.Report;

public interface BorrowingRepository extends JpaRepository<Report, Long> {

    @Query("SELECT r FROM Report r WHERE r.reportDate < :currentDate")
 List<Report> findOverdueReport(@Param("currentDate") LocalDate currentDate);
    
}

