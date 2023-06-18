package com.ncc.service;

import com.ncc.dto.CheckInOutDTO;
import com.ncc.dto.EmployeeCheckInCheckOutDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

public interface ICheckInOutService {
    CheckInOutDTO checkIn(Integer employeeCode);

    CheckInOutDTO checkOut(Integer employeeCode);
//
//    List<CheckInOutDTO > getCheckInRecordsByEmployeeAndDateRange(Integer employeeCode, LocalDate startDate, LocalDate endDate);
//
//    List<CheckInOutDTO> getCheckInErrorsByEmployeeAndMonth(Integer employeeCode, YearMonth yearMonth);

    List<EmployeeCheckInCheckOutDTO> getAllEmployeesCheckInCheckOutByDateRange(LocalDate startDate, LocalDate endDate);

    //    List<EmployeeCheckInCheckOutDTO> getLateCheckInsByMonth(int month, LocalTime checkInTimeThreshold);
}
