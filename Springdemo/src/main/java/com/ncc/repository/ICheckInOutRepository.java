package com.ncc.repository;

import com.ncc.entity.CheckInOut;
import com.ncc.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICheckInOutRepository extends JpaRepository<CheckInOut, Integer> {
    boolean existsByEmployeeAndCheckOutTimeIsNull(Employee employee);

    CheckInOut findByEmployeeAndCheckOutTimeIsNull(Employee employee);
}
