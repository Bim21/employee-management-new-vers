package com.ncc.controller;

import com.ncc.dto.CheckInOutDTO;
import com.ncc.dto.EmployeeCheckInCheckOutDTO;
import com.ncc.service.ICheckInOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/checkInOut")
public class CheckInOutController {
    private final ICheckInOutService checkInOutService;

    @PostMapping("/{employeeCode}/checkIn")
    public CheckInOutDTO checkIn(@PathVariable String employeeCode){
        Integer code = Integer.parseInt(employeeCode);
        return checkInOutService.checkIn(code);
    }

    @PostMapping("/{employeeCode}/checkOut")
    public CheckInOutDTO checkOut(@PathVariable String employeeCode){
        Integer code = Integer.parseInt(employeeCode);
        return checkInOutService.checkOut(code);
    }
    @GetMapping("/checkIn-records/{employeeCode}")
    public List<CheckInOutDTO> getCheckInOutByEmployeeAndDateRange(
            @PathVariable String employeeCode,
            @RequestParam(required = false)LocalDate startDate,
            @RequestParam(required = false)LocalDate endDate
            ){
        if(startDate == null){
            // Nếu startDate không được cung cấp, sử dụng ngày đầu tuần hiện tại
            startDate = LocalDate.now().with(DayOfWeek.MONDAY);
        }
        if(endDate == null){
            // Nếu endDate không được cung cấp, sử dụng ngày hiện tại
            endDate = LocalDate.now();
        }
        return checkInOutService.getCheckInRecordsByEmployeeAndDateRange(Integer.valueOf(employeeCode), startDate, endDate);
    }

    @GetMapping("/checkIn-errors/{employeeCode}")
    public List<CheckInOutDTO> getCheckInErrorsByEmployeeAndMonth(
            @PathVariable String employeeCode,
            @RequestParam(required = false)YearMonth yearMonth
            ){
        if(yearMonth == null){
            //Nếu yearMonth ko được cung cấp, sử dụng tháng hiện tại
            yearMonth = YearMonth.now();
        }
        return checkInOutService.getCheckInErrorsByEmployeeAndMonth(Integer.valueOf(employeeCode), yearMonth);
    }
    @GetMapping("/listCheckInCheckOut")
    public List<EmployeeCheckInCheckOutDTO> getALlEmployeeCheckInCheckOutByDateRange(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ){
        if (startDate == null){
            startDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        }
        if(endDate == null){
            endDate = LocalDate.now();
        }
        List<EmployeeCheckInCheckOutDTO> checkInCheckOutDTOs = checkInOutService.getAllEmployeesCheckInCheckOutByDateRange(startDate, endDate);
        return checkInCheckOutDTOs;
    }

    @GetMapping("/lateCheckIns")
    public List<EmployeeCheckInCheckOutDTO> getLateCheckInsByMonth(@RequestParam("month") int month, @RequestParam("checkInTimeThreshold") @DateTimeFormat(pattern = "HH:mm")LocalTime checkInTimeThreshold){
        return checkInOutService.getLateCheckInsByMonth(month,checkInTimeThreshold);
    }
}
