package com.ncc.service;

import com.ncc.dto.CheckInOutDTO;

import java.time.LocalDate;
import java.util.List;

public interface ICheckInOutService {
    CheckInOutDTO checkIn(Integer employeeCode);
    CheckInOutDTO checkOut(Integer employeeCode);

    List<CheckInOutDTO > getCheckInRecordsByEmployeeAndDateRange(Integer employeeCode, LocalDate startDate, LocalDate endDate);

}
