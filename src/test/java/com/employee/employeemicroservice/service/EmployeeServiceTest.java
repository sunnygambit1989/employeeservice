package com.employee.employeemicroservice.service;

import com.employee.employeemicroservice.config.PropertySource;
import com.employee.employeemicroservice.dto.EmployeeDTO;
import com.employee.employeemicroservice.service.impl.EmployeeServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private PropertySource propertySource;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private EmployeeDTO employee;

    private List<EmployeeDTO> employeeList;

    @BeforeEach
    public void setUp() {
        employee = new EmployeeDTO();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setSalary(50000.0);

        employeeList = new ArrayList<>();
        employeeList.add(employee);

        when(propertySource.getFilePath()).thenReturn("test-employees.json");
    }

    @Test
    public void testCreateEmployee() throws IOException {
        when(objectMapper.readValue(any(File.class), eq(new TypeReference<List<EmployeeDTO>>() {})))
                .thenReturn(employeeList);

        String result = employeeService.createEmployee(employee);

        assertNotNull(result);
        verify(objectMapper, times(1)).writeValue(any(File.class), anyList());
    }

    @Test
    public void testGetEmployee() throws IOException {
        when(objectMapper.readValue(any(File.class), eq(new TypeReference<List<EmployeeDTO>>() {})))
                .thenReturn(employeeList);

        EmployeeDTO result = employeeService.getEmployee(1L);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }

    @Test
    public void testGetEmployeesByName() throws IOException {
        when(objectMapper.readValue(any(File.class), eq(new TypeReference<List<EmployeeDTO>>() {})))
                .thenReturn(employeeList);

        List<EmployeeDTO> results = employeeService.getEmployees("John", null, null);

        assertEquals(1, results.size());
        assertEquals("John", results.get(0).getFirstName());
    }

    @Test
    public void testGetEmployeesBySalaryRange() throws IOException {
        when(objectMapper.readValue(any(File.class), eq(new TypeReference<List<EmployeeDTO>>() {})))
                .thenReturn(employeeList);

        List<EmployeeDTO> results = employeeService.getEmployees(null, 40000.0, 60000.0);

        assertEquals(1, results.size());
        assertEquals(50000.0, results.get(0).getSalary());
    }

    @Test
    public void testGetEmployeesByNameAndSalaryRange() throws IOException {
        when(objectMapper.readValue(any(File.class), eq(new TypeReference<List<EmployeeDTO>>() {})))
                .thenReturn(employeeList);

        List<EmployeeDTO> results = employeeService.getEmployees("John", 40000.0, 60000.0);

        assertEquals(1, results.size());
        assertEquals("John", results.get(0).getFirstName());
        assertEquals(50000.0, results.get(0).getSalary());
    }
}
