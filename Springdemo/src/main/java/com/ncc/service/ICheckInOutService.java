package com.ncc.service;

import com.ncc.dto.CheckInOutDTO;

public interface ICheckInOutService {
    CheckInOutDTO checkIn(Integer employeeCode);
    CheckInOutDTO checkOut(Integer employeeCode);
}
