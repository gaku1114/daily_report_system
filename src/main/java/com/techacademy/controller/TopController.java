package com.techacademy.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.techacademy.entity.Employee;
import com.techacademy.service.EmployeeDetail;
import com.techacademy.service.ReportService;

@Controller
public class TopController {

    private final ReportService reportService;

    public TopController(ReportService service) {
        this.reportService = service;
    }

    @GetMapping("/")
    public String getMyReport(Model model, @AuthenticationPrincipal EmployeeDetail employee) {
        Employee current_employee = employee.getEmployee();
        model.addAttribute("reportlist", reportService.getMyReportList(current_employee));
        model.addAttribute("count", reportService.getMyReportCount(current_employee));
        return "top";
    }
}
