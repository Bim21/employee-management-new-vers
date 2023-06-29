package com.ncc.dto;

import com.ncc.entity.Address;
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
    private Address addresses;
    private String password;
    private String employeeCode;
    private List<Integer> roleIds;

    public void getRoleIds(List<Integer> roleIds) {
        int n = 1;
        for (Integer roleId : roleIds) {
            roleId = n++;
        }
        this.roleIds = roleIds;
    }

}
