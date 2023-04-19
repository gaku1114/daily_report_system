package com.techacademy.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techacademy.entity.Authentication;
import com.techacademy.entity.Employee;
import com.techacademy.repository.EmployeeRepository;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getEmployeeList(){
        return employeeRepository.findAll();
    }

    public Employee getEmployee(Integer id) {
        return employeeRepository.findById(id).get();
    }

    @Transactional
    public void saveEmployee(Employee employee) {
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());
        employee.setDeleteFlag(0);

        Authentication authentication = employee.getAuthentication();
        authentication.setEmployee(employee);
        employeeRepository.save(employee);
    }

    @Transactional
    public void updateEmployee(Integer id, Employee employee) {
        Employee saveEmployee = employeeRepository.findById(id).orElse(null);
        saveEmployee.setName(employee.getName());
        saveEmployee.setUpdatedAt(LocalDateTime.now());
        saveEmployee.setDeleteFlag(0);

        Authentication saveAuthentication = saveEmployee.getAuthentication();
        Authentication authentication = employee.getAuthentication();
        saveAuthentication.setEmployee(saveEmployee);
        saveAuthentication.setPassword(authentication.getPassword());
        saveAuthentication.setRole(authentication.getRole());

        employeeRepository.save(saveEmployee);
    }
}
