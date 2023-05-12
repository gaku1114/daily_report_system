package com.techacademy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.repository.ReportRepository;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public List<Report> getReportList(){
        return reportRepository.findAll();
    }

    public Long getReportCount() {
        return reportRepository.count();
    }

    public Report getReport(Integer id) {
        return reportRepository.findById(id).get();
    }

    public void saveReport(Report report) {
        reportRepository.save(report);
    }

    public List<Report> getMyReportList(Employee employee){
        return reportRepository.findByEmployee(employee);
    }

    public Long getMyReportCount(Employee employee) {
        return reportRepository.countByEmployee(employee);
    }
}
