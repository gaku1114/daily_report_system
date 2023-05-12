package com.techacademy.controller;

import java.time.LocalDateTime;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Report;
import com.techacademy.service.EmployeeDetail;
import com.techacademy.service.ReportService;

@Controller
@RequestMapping("report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService service) {
        this.reportService = service;
    }

    /** 一覧画面を表示 */
    @GetMapping("/list")
    public String getList(Model model) {
        // 全建検索結果をModelに登録
        model.addAttribute("reportlist", reportService.getReportList());
        model.addAttribute("count", reportService.getReportCount());
        // employee/list.htmlに画面遷移
        return "report/list";
    }

    /** 詳細画面を表示 */
    @GetMapping("/detail/{id}")
    public String getReport(@PathVariable(name = "id") Integer id, Model model, @AuthenticationPrincipal EmployeeDetail employee) {
        model.addAttribute("report", reportService.getReport(id));
        model.addAttribute("userid", employee.getEmployee().getId());
        return "report/detail";
    }

    @GetMapping("/register")
    public String getRegister(Model model, Report report, @AuthenticationPrincipal EmployeeDetail employee) {
        model.addAttribute("report", report);
        model.addAttribute("username", employee.getEmployee().getName());
        return "report/register";
    }

    @PostMapping("/register")
    public String postRegister(@Validated Report report, BindingResult res, Model model, @AuthenticationPrincipal EmployeeDetail employee) {
        if(res.hasErrors()) {
            // エラーあり
            return getRegister(model, report, employee);
        }

        report.setCreatedAt(LocalDateTime.now());
        report.setUpdatedAt(LocalDateTime.now());
        report.setEmployee(employee.getEmployee());

        reportService.saveReport(report);
        return "redirect:/report/list";
    }

    @GetMapping("/update/{id}")
    public String getUpdate(@PathVariable(name="id") Integer id, Model model) {
        model.addAttribute("report", reportService.getReport(id));
        return "/report/update";
    }

    @PostMapping("update/{id}")
    public String postUpdate(@PathVariable(name="id") Integer id, @Validated Report report, BindingResult res, Model model, @AuthenticationPrincipal EmployeeDetail employee) {
        if(res.hasErrors()) {
            // エラーあり
            return getUpdate(id, model);
        }

        report.setUpdatedAt(LocalDateTime.now());
        report.setEmployee(employee.getEmployee());

        reportService.saveReport(report);
        return "redirect:/report/list";
    }


}
