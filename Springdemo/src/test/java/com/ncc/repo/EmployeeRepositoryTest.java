package com.ncc.repo;

import com.ncc.entity.Employee;

import com.ncc.repository.IEmployeeRepository;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Vô hiệu hóa việc thay thế DataSource nhúng
public class EmployeeRepositoryTest {
    @Autowired
    private IEmployeeRepository employeeRepository;

    @Test
    public void testFindAll() {
        // Saving test data to the database
        employeeRepository.save(new Employee(1,"John", "Doe","JohnDoe","123456", "chiennguye2@gmail.com", 1234, true, null, null, null));
        employeeRepository.save(new Employee(2,"Lan", "Nguyen","HongLan","123456", "honglannguyen@gmail.com", 4444, true, null, null, null));

        // Calling the method to be tested
        List<Employee> employees = employeeRepository.findAll();

        // Assertions
        assertEquals(2, employees.size());
        // Add more assertions as needed
    }
}



