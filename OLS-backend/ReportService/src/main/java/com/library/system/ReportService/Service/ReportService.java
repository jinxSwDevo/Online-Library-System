package com.library.system.ReportService.Service;

import java.util.List;

import com.library.system.ReportService.entity.Report;

public interface ReportService {
    List<Report> getAllReports();
    List<Report> generateOverdueReport();
}
