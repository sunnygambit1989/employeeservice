package com.employee.employeemicroservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class EmployeeDTO {

    private Long id;

    @NotNull(message = "First name cannot be null")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    private String lastName;

    @NotNull(message = "Salary cannot be null")
    private Integer salary;

    @NotNull(message = "Join date cannot be null")
    private String joinDate;

    @NotNull(message = "Date of birth cannot be null")
    private String dateOfBirth;

}
