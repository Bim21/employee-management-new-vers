package com.ncc.repository;

import com.ncc.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByUserNameContainingIgnoreCase(String keyword);

    Employee findByEmployeeCode(Integer employeeCode);

    Employee findEmployeeByUserName(String username);

    boolean existsEmployeeByUserName(String userName);

    boolean existsEmployeeByEmail(String email);
}
