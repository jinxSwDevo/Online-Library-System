package com.library.system.ReportService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.system.ReportService.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
