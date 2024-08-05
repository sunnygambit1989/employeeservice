package com.employee.employeemicroservice.controller;


import com.employee.employeemicroservice.dto.EmployeeDTO;
import com.employee.employeemicroservice.service.impl.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("employee")
@RequiredArgsConstructor
public class EmployeeController {


    private final EmployeeService employeeService;

    @PostMapping("employees")
    public String createEmployee(@RequestBody @Valid EmployeeDTO employeeCreateDTO) {
        return employeeService.createEmployee(employeeCreateDTO);

    }

    @GetMapping("employees/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable("id") Long id) {
        return  ResponseEntity.ok(employeeService.getEmployee(id));
    }

    @GetMapping("employees")
    public ResponseEntity<List<EmployeeDTO>> getEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double fromSalary,
            @RequestParam(required = false) Double toSalary) {

        List<EmployeeDTO> employees = employeeService.getEmployees(name, fromSalary, toSalary);
        return ResponseEntity.ok(employees);
    }
}
