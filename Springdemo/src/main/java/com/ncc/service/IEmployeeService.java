package com.ncc.service;

import com.ncc.dto.CheckInOutDTO;
import com.ncc.dto.EmployeeRequestDTO;
import com.ncc.dto.EmployeeResponseDTO;
import com.ncc.entity.Employee;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.List;

public interface IEmployeeService {

    List<EmployeeResponseDTO> syncData(EmployeeRequestDTO employeeRequestDTO);
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) throws MessagingException;

    EmployeeResponseDTO updateEmployee(EmployeeRequestDTO employeeRequestDTO);

    void deleteEmployeeById(int id);

    List<EmployeeResponseDTO> searchEmployeesByName(String keyword);

    List<EmployeeResponseDTO> getAllEmployee();
    List<CheckInOutDTO> getCheckInOutsByEmployeeId(int employeeId);

    List<EmployeeResponseDTO> getEmployeesWithCheckInOuts(LocalDate startDate, LocalDate endDate);
    List<Employee> getEmployeesWithoutCheckInOut();
}
