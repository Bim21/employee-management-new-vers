package com.ncc.controller;

import com.ncc.dto.CheckInOutDTO;
import com.ncc.dto.EmployeeCheckInCheckOutDTO;
import com.ncc.entity.CheckInOut;
import com.ncc.projection.CheckInOutProjection;
import com.ncc.service.ICheckInOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
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

    //    @GetMapping("/employee/{employeeId}")
//    public List<CheckInOut> getCheckInOutListByEmployeeIdAndDateRange(@PathVariable Integer employeeId,
//                                                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
//                                                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
//        return checkInOutService.getCheckInOutListByEmployeeIdAndDateRange(employeeId, startDate, endDate);
//    }
    @GetMapping("/employee/{employeeId}")
    public List<CheckInOutProjection> getCheckInOutByEmployeeAndDateRange(
            @PathVariable Integer employeeId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        return checkInOutService.getCheckInOutByEmployeeAndDateRange(employeeId, startDate, endDate);
    }

    @GetMapping("/employees/{employeeId}/errors/{year}/{month}")
    public List<CheckInOut> getErrorCheckInsByEmployeeAndMonth(
            @PathVariable Integer employeeId,
            @PathVariable int year,
            @PathVariable int month
    ) {
        return checkInOutService.getErrorCheckInsByEmployeeAndMonth(employeeId, year, month);
    }
}
