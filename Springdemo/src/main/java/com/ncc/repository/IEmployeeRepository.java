package com.ncc.repository;

import com.ncc.dto.EmployeeResponseDTO;
import com.ncc.entity.Employee;
import com.ncc.projection.EmployeeWithoutCheckInOutProjection;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByUserNameContainingIgnoreCase(String keyword);

    Employee findByEmployeeCode(Integer employeeCode);

    Employee findEmployeeByUserName(String username);

    boolean existsEmployeeByUserName(String userName);

    boolean existsEmployeeByEmail(String email);

    @Query("SELECT e, c.checkInTime, c.checkOutTime FROM Employee e LEFT JOIN e.checkInOuts c WHERE c.checkInTime >= :startDate AND c.checkOutTime <= :endDate")
    List<Employee> getEmployeeCheckInOutByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT e FROM Employee e WHERE NOT EXISTS (SELECT c FROM CheckInOut c WHERE c.employee = e)")
    List<Employee> getEmployeesCheckInOutError();

    // TODO: Cache Query, Projection
    @Cacheable("getEmployee")
    @Query("SELECT e.id AS id, e.firstName AS firstName, e.lastName AS lastName, e.email AS email FROM Employee e WHERE NOT EXISTS (SELECT 1 FROM CheckInOut c WHERE c.employee = e)")
    List<EmployeeWithoutCheckInOutProjection> getEmployeesWithoutCheckInOut();
}
