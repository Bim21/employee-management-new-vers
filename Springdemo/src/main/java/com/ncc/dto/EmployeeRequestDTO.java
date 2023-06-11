package com.ncc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String employeeCode;
    private List<Integer> roleIds;
}
