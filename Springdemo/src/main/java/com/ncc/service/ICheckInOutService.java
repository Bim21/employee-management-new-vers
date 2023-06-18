package com.ncc.service;

import com.ncc.dto.CheckInOutDTO;
import com.ncc.dto.EmployeeCheckInCheckOutDTO;
import com.ncc.entity.CheckInOut;
import com.ncc.projection.CheckInOutProjection;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

public interface ICheckInOutService {
    CheckInOutDTO checkIn(Integer employeeCode);

    CheckInOutDTO checkOut(Integer employeeCode);

//    List<CheckInOut> getCheckInOutListByEmployeeIdAndDateRange(Integer employeeId, LocalDate startDate, LocalDate endDate);
    List<CheckInOutProjection> getCheckInOutByEmployeeAndDateRange(Integer employeeId, LocalDate startDate, LocalDate endDate);

    List<CheckInOut> getErrorCheckInsByEmployeeAndMonth(Integer employeeId, int year, int month);
}
