package com.ncc.dto;

import com.ncc.entity.Address;
import com.ncc.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Address addresses;

}
