package com.employee.employeemicroservice.service.impl;

import com.employee.employeemicroservice.config.PropertySource;
import com.employee.employeemicroservice.dto.EmployeeDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private ObjectMapper objectMapper = new ObjectMapper();

    private final PropertySource propertySource;
    @Override
    public String createEmployee(EmployeeDTO employeeCreateDTO) {

        return saveOrUpdateEmployee(employeeCreateDTO) ;
    }

    @Override
    public EmployeeDTO getEmployee(Long id) {
        for(EmployeeDTO employeeDTO: loadEmployeesFromFile()) {

            if(employeeDTO.getId().longValue()==id.longValue()) {
                return employeeDTO;
            }
        }
        return new EmployeeDTO();
    }

    public List<EmployeeDTO> getEmployees(String name, Double fromSalary, Double toSalary) {
        List<EmployeeDTO> employees = loadEmployeesFromFile();

        // Filter by name if provided
        if (name != null && !name.isEmpty()) {
            employees = employees.stream()
                    .filter(e -> e.getFirstName().contains(name) || e.getLastName().contains(name))
                    .collect(Collectors.toList());
        }

        // Filter by salary range if provided
        if (fromSalary != null || toSalary != null) {
            return employees.stream()
                    .filter(employee ->
                            (fromSalary == null || employee.getSalary() >= fromSalary) &&
                                    (toSalary == null || employee.getSalary() <= toSalary)
                    )
                    .collect(Collectors.toList());
        }

        return employees;
    }


    private List<EmployeeDTO> loadEmployeesFromFile() {
        try {
            File file = new File(propertySource.getFilePath());
            if (file.exists()) {
                return objectMapper.readValue(file, new TypeReference<List<EmployeeDTO>>() {});
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            log.error("Error while loading file");
            throw new RuntimeException("Failed to load employees", e);
        }

        }

    public String saveOrUpdateEmployee(EmployeeDTO employee) {
        try {
            Long empId = System.currentTimeMillis();
            employee.setId(empId);

            // Read existing employees from the file
            File file = new File(propertySource.getFilePath());
            List<EmployeeDTO> existingEmployees = new ArrayList<>();

            if (file.exists()) {
                // Deserialize existing employees if the file is not empty
                existingEmployees = objectMapper.readValue(file, new TypeReference<List<EmployeeDTO>>() {});
            }

            // Add the new employee to the list
            existingEmployees.add(employee);

            // Write the updated list back to the file
            objectMapper.writeValue(file, existingEmployees);

            return String.format("{\"id\": \"%s\"}", empId);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save or update employee", e);
        }
    }

}

