package com.ncc.controller;

import com.ncc.dto.CheckInOutDTO;
import com.ncc.dto.EmployeeDTO;
import com.ncc.dto.EmployeeRequestDTO;
import com.ncc.dto.EmployeeResponseDTO;
import com.ncc.entity.Employee;
import com.ncc.projection.EmployeeWithoutCheckInOutProjection;
import com.ncc.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final IEmployeeService employeeService;

    @Autowired
    private MessageSource messageSource;

    @PostMapping("/employee")
    public List<EmployeeResponseDTO> syncData(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        return employeeService.syncData(employeeRequestDTO);
    }

    @PostMapping
    public EmployeeResponseDTO createEmployee(@RequestBody EmployeeRequestDTO employeeRequestDTO) throws MessagingException {
        return employeeService.createEmployee(employeeRequestDTO);
    }


    @PutMapping("/{id}")
    public EmployeeResponseDTO updateEmployee(@PathVariable("id") int id, @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        employeeRequestDTO.setId(id);
        return employeeService.updateEmployee(employeeRequestDTO);

    }

    @DeleteMapping("/{id}")
    public void deleteEmployeeById(@PathVariable int id) {
        employeeService.deleteEmployeeById(id);
    }

    @GetMapping("/search")
    public List<EmployeeResponseDTO> searchEmployeesByName(@RequestParam("keyword") String keyword) {
        return employeeService.searchEmployeesByName(keyword);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployee() {
        return employeeService.getAllEmployee();
    }

    @GetMapping("/{employeeId}/checkInOuts")
    public List<CheckInOutDTO> getCheckInOuts(@PathVariable("employeeId") int employeeId) {
        return employeeService.getCheckInOutsByEmployeeId(employeeId);
    }

    @GetMapping("/checkInOuts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<EmployeeResponseDTO> getEmployeesWithCheckInOuts(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            Pageable pageable
    ) {
        return employeeService.getEmployeesWithCheckInOuts(startDate, endDate, pageable);
    }

    @GetMapping("/without-checkinout")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<EmployeeWithoutCheckInOutProjection> getEmployeesWithoutCheckInOut() {
        return employeeService.getEmployeesWithoutCheckInOut();
    }
    @GetMapping("/checkin-errors")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Employee> getEmployeesCheckInOutError() {
        return employeeService.getEmployeesWithoutError();
    }
}
