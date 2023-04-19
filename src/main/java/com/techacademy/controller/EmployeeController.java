package com.techacademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Authentication;
import com.techacademy.entity.Employee;
import com.techacademy.service.EmployeeService;

@Controller
@RequestMapping("employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService service) {
        this.employeeService = service;
    }

    /** 一覧画面を表示 */
    @GetMapping("/list")
    public String getList(Model model) {
        // 全建検索結果をModelに登録
        model.addAttribute("employeelist", employeeService.getEmployeeList());
        // employee/list.htmlに画面遷移
        return "employee/list";
    }

    @GetMapping("/detail/{id}")
    public String getEmployee(@PathVariable(name = "id") Integer id, Model model) {
        model.addAttribute("employee", employeeService.getEmployee(id));
        return "employee/detail";
    }

    @GetMapping("/register")
    public String getRegister(Model model, Employee employee) {
        model.addAttribute("employee", employee);
        return "employee/register";
    }

    @PostMapping("/register")
    public String postRegister(@Validated Employee employee, BindingResult res, Model model) {
        if(res.hasErrors()) {
            // エラーあり
            return getRegister(model, employee);
        }
        employeeService.saveEmployee(employee);
        return "redirect:/employee/list";
    }

    @GetMapping("/update/{id}")
    public String getUpdate (@PathVariable(name = "id", required = false) Integer id, Model model) {
        Employee employee = employeeService.getEmployee(id);
        model.addAttribute("employee", employee);

        Authentication authentication = employee.getAuthentication();
        model.addAttribute("authentication", authentication);
        return "employee/update";
    }

    @PostMapping("/update/{id}")
    public String postUpdate (@PathVariable(name = "id") Integer id, Employee employee) {
        employeeService.updateEmployee(id, employee);
        return "redirect:/employee/list";
    }

    @GetMapping("/delete/{id}")
    public String getDelete(@PathVariable(name = "id") Integer id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employee/list";
    }
}
