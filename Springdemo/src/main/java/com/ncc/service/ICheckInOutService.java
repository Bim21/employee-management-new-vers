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

}
