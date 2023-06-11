package com.ncc.repository;

import com.ncc.entity.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRoleRepository extends JpaRepository<EmployeeRole, Integer> {

//    @Modifying
//    @Query("DELETE FROM EmployeeRole" +
//            "WHERE employee.employeeId = :employeeId")
//    void findEmployeeRolesByEmployeeId(@Param("employeeId") Integer employeeId);
}
