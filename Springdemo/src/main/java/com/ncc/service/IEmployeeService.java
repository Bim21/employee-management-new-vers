package com.ncc.service;

import com.ncc.dto.EmployeeDTO;
import com.ncc.entity.Employee;

import java.util.List;

public interface IEmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployee(EmployeeDTO employeeDTO);

    void deleteEmployeeById(int id);

    void saveUser(List<Employee> employees);

    List<EmployeeDTO> searchEmployeesByName(String keyword);
}
