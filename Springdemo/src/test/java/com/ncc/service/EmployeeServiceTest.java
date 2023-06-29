package com.ncc.service;

import com.ncc.dto.EmployeeDTO;
import com.ncc.entity.Employee;
import com.ncc.repository.IEmployeeRepository;
import com.ncc.service.impl.EmployeeService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @Mock
    private IEmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private ModelMapper mapper;

    @BeforeEach
    public void setup() {
        mapper = new ModelMapper();
    }

    @Test
    public void testFindAll() {
        // Create mock data
        List<Employee> mockEmployees = new ArrayList<>();
        mockEmployees.add(new Employee(1, "John", "Doe", "JohnDoe", "123456", "chiennguye2@gmail.com", 1234, true, null, null, null));
        mockEmployees.add(new Employee(2, "Lan", "Nguyen", "HongLan", "123456", "honglannguyen@gmail.com", 4444, true, null, null, null));

        // Configure the mock behavior
        when(employeeRepository.findAll()).thenReturn(mockEmployees);

        // Call the method to be tested
        List<EmployeeDTO> employees = employeeService.getAllEmployee();

        // Assertions
        assertEquals(2, employees.size());
        // Add more assertions as needed
    }
}
