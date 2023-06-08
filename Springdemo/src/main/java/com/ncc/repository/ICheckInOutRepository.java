package com.ncc.repository;

import com.ncc.dto.CheckInOutDTO;
import com.ncc.entity.CheckInOut;
import com.ncc.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ICheckInOutRepository extends JpaRepository<CheckInOut, Integer> {
    boolean existsByEmployeeAndCheckOutTimeIsNull(Employee employee);

    CheckInOut findByEmployeeAndCheckOutTimeIsNull(Employee employee);

    List<CheckInOutDTO> findByEmployeeIdAndDateBetween(int id, LocalDate startDate, LocalDate endDate);
}
