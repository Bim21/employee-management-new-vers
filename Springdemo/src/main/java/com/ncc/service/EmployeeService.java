package com.ncc.service;

import com.ncc.constants.MessageConstant;
import com.ncc.dto.CheckInOutDTO;
import com.ncc.dto.EmployeeCheckInCheckOutDTO;
import com.ncc.dto.EmployeeDTO;
import com.ncc.entity.CheckInOut;
import com.ncc.entity.Employee;
import com.ncc.exception.NotFoundException;
import com.ncc.repository.ICheckInOutRepository;
import com.ncc.repository.IEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {
    private final IEmployeeRepository employeeRepository;
    private final ICheckInOutRepository checkInOutRepository;
    private final ModelMapper mapper;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        String employeeCode = generateEmployeeCode();
        Employee employee = mapper.map(employeeDTO, Employee.class);
        employee.setEmployeeCode(Integer.valueOf(employeeCode));
        Employee saveEmployee = employeeRepository.save(employee);
        EmployeeDTO saveEmployeeDTO = mapper.map(saveEmployee, EmployeeDTO.class);
        return saveEmployeeDTO;
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeDTO.getId());
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            mapper.map(employeeDTO, employee);
            Employee updateEmployee = employeeRepository.save(employee);
            EmployeeDTO updateEmployeeDTO = mapper.map(updateEmployee, EmployeeDTO.class);
            return updateEmployeeDTO;
        } else {
            throw new NotFoundException(MessageConstant.EMPLOYEE_IS_NULL);
        }
    }

    @Override
    public void deleteEmployeeById(int id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            employeeRepository.delete(optionalEmployee.get());
        } else {
            throw new NotFoundException(MessageConstant.EMPLOYEE_IS_NULL);
        }
    }

    @Override
    public void saveUser(List<Employee> employees) {
        employeeRepository.saveAll(employees);
    }

    @Override
    public List<EmployeeDTO> searchEmployeesByName(String keyword) {
        List<Employee> employees = employeeRepository.findByUserNameContainingIgnoreCase(keyword);
        return employees.stream()
                .map(employee -> mapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    private String generateEmployeeCode() {
        Random random = new Random();
        int code = random.nextInt(9000) + 1000;
        return String.valueOf(code);
    }
}
