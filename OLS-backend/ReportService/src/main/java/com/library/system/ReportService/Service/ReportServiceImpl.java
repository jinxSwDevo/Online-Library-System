package com.library.system.ReportService.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.library.system.ReportService.Repository.BorrowingRepository;
import com.library.system.ReportService.Repository.ReportRepository;
import com.library.system.ReportService.entity.Report;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final BorrowingRepository borrowingRepository;

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public List<Report> generateOverdueReport() {
        LocalDate today = LocalDate.now();
        return borrowingRepository.findOverdueReport(today);
    }
}

    
