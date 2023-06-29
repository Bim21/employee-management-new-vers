package com.ncc.service;

import com.ncc.dto.CheckInOutDTO;
import com.ncc.dto.EmployeeDTO;
import com.ncc.dto.EmployeeRequestDTO;
import com.ncc.dto.EmployeeResponseDTO;
import com.ncc.entity.CheckInOut;
import com.ncc.entity.Employee;
import com.ncc.projection.EmployeeWithoutCheckInOutProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.List;

public interface IEmployeeService {

    List<EmployeeResponseDTO> syncData(EmployeeRequestDTO employeeRequestDTO);

    EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) throws MessagingException;

    EmployeeResponseDTO updateEmployee(EmployeeRequestDTO employeeRequestDTO);

    void deleteEmployeeById(int id);

    List<EmployeeResponseDTO> searchEmployeesByName(String keyword);

    List<EmployeeDTO> getAllEmployee();

    List<CheckInOutDTO> getCheckInOutsByEmployeeId(int id);

    Page<EmployeeResponseDTO> getEmployeesWithCheckInOuts(LocalDate startDate, LocalDate endDate, Pageable pageable);


    List<Employee> getEmployeesWithoutError();
    List<EmployeeWithoutCheckInOutProjection> getEmployeesWithoutCheckInOut();

    List<CheckInOut> getCheckInOutsByDate(LocalDate date);

}
