package com.ncc.repository;

import com.ncc.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByUserNameContainingIgnoreCase(String keyword);

    Employee findByEmployeeCode(Integer employeeCode);

    Employee findEmployeeByUserName(String username);

    boolean existsEmployeeByUserName(String userName);

    boolean existsEmployeeByEmail(String email);
}
