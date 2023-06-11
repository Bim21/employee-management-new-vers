package com.ncc.repository;

import com.ncc.dto.CheckInOutDTO;
import com.ncc.entity.CheckInOut;
import com.ncc.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

@Repository
public interface ICheckInOutRepository extends JpaRepository<CheckInOut, Integer> {
    boolean existsByEmployeeAndCheckOutTimeIsNull(Employee employee);

    CheckInOut findByEmployeeAndCheckOutTimeIsNull(Employee employee);

    List<CheckInOutDTO> findByEmployeeIdAndDateBetween(Employee id, LocalDate startDate, LocalDate endDate);

    List<CheckInOutDTO> findCheckInErrorsByEmployeeAndMonth(Employee employee, YearMonth yearMonth);

    List<CheckInOut> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT cio FROM CheckInOut cio WHERE cio.employee = :employee AND MONTH(cio.date) = :month AND cio.checkInTime > :checkInTimeThreshold")
    List<CheckInOut> findLateCheckInsByMonth(@Param("employee") Employee employee, @Param("month") int month, @Param("checkInTimeThreshold") LocalTime checkInTimeThreshold);

}
