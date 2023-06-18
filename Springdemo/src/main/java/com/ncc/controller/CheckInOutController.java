package com.ncc.controller;

import com.ncc.dto.CheckInOutDTO;
import com.ncc.dto.EmployeeCheckInCheckOutDTO;
import com.ncc.service.ICheckInOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/checkInOut")
public class CheckInOutController {
    private final ICheckInOutService checkInOutService;

    @PostMapping("/{employeeCode}/checkIn")
    public CheckInOutDTO checkIn(@PathVariable String employeeCode) {
        Integer code = Integer.parseInt(employeeCode);
        return checkInOutService.checkIn(code);
    }

    @PostMapping("/{employeeCode}/checkOut")
    public CheckInOutDTO checkOut(@PathVariable String employeeCode) {
        Integer code = Integer.parseInt(employeeCode);
        return checkInOutService.checkOut(code);
    }

}
