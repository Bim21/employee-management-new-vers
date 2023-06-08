package com.ncc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String employeeCode;
    private List<CheckInOutDTO> checkInRecords;
    private List<CheckInOutDTO> checkOutRecords;
}
