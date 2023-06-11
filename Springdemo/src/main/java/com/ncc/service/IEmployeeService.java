package com.ncc.service;

import com.ncc.dto.EmployeeCheckInCheckOutDTO;
import com.ncc.dto.EmployeeDTO;
import com.ncc.dto.EmployeeRequestDTO;
import com.ncc.dto.EmployeeResponseDTO;
import com.ncc.entity.Employee;

import java.time.LocalDate;
import java.util.List;

public interface IEmployeeService {

    List<EmployeeResponseDTO> syncData(EmployeeRequestDTO employeeRequestDTO);
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO);

    EmployeeResponseDTO updateEmployee(EmployeeRequestDTO employeeRequestDTO);

    void deleteEmployeeById(int id);

    void saveUser(List<Employee> employees);

    List<EmployeeResponseDTO> searchEmployeesByName(String keyword);


}
