package com.library.system.ReportService.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.system.ReportService.Service.ReportService;
import com.library.system.ReportService.entity.Report;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/reports")
public class ReportController {
      private final ReportService reportService;
      
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportService.getAllReports();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Report>> generateOverdueReport() {
        List<Report> overdueReports = reportService.generateOverdueReport();
        return new ResponseEntity<>(overdueReports, HttpStatus.OK);
    }
}
