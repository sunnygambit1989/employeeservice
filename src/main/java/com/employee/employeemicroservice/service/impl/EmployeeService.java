package com.employee.employeemicroservice.service.impl;

import com.employee.employeemicroservice.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    String createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO getEmployee(Long id);

    List<EmployeeDTO> getEmployees(String name, Double fromSalary, Double toSalary);
}
