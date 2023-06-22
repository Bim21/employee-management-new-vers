package com.ncc.repository;

import com.ncc.dto.CheckInOutDTO;
import com.ncc.entity.CheckInOut;
import com.ncc.entity.Employee;
import com.ncc.projection.CheckInOutProjection;
import org.springframework.cache.annotation.Cacheable;
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

    //    @Query(value = "SELECT * FROM check_in_out WHERE employee_id = :employeeId AND date BETWEEN :startDate AND :endDate", nativeQuery = true)
//    List<CheckInOut> findByEmployeeAndDateBetweenNative(Integer employeeId, LocalDate startDate, LocalDate endDate);
    @Query(value = "SELECT check_in_time, check_out_time FROM check_in_out WHERE employee_id = :employeeId AND date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<CheckInOutProjection> findCheckInOutByEmployeeAndDateBetween(@Param("employeeId") Integer employeeId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    @Cacheable("checkInOutErrors")
    @Query("SELECT c FROM CheckInOut c WHERE c.employee = :employee AND YEAR(c.checkInTime) = :year AND MONTH(c.checkInTime) = :month AND c.isError = true")
    List<CheckInOut> findErrorCheckInsByEmployeeAndMonth(@Param("employee") Employee employee, @Param("year") int year, @Param("month") int month);

}
