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

//    @GetMapping("/checkIn-records/{employeeCode}")
//    public List<CheckInOutDTO> getCheckInOutByEmployeeAndDateRange(
//            @PathVariable String employeeCode,
//            @RequestParam(required = false) LocalDate startDate,
//            @RequestParam(required = false) LocalDate endDate
//    ) {
//
//        return checkInOutService.getCheckInRecordsByEmployeeAndDateRange(Integer.valueOf(employeeCode), startDate, endDate);
//    }
//
//    @GetMapping("/checkIn-errors/{employeeCode}")
//    public List<CheckInOutDTO> getCheckInErrorsByEmployeeAndMonth(
//            @PathVariable String employeeCode,
//            @RequestParam(required = false) YearMonth yearMonth
//    ) {
//
//        return checkInOutService.getCheckInErrorsByEmployeeAndMonth(Integer.valueOf(employeeCode), yearMonth);
//    }

    // TODO: lỗi status: 999

    @GetMapping("/listCheckInCheckOut")
    public List<EmployeeCheckInCheckOutDTO> getALlEmployeeCheckInCheckOutByDateRange(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {

        List<EmployeeCheckInCheckOutDTO> checkInCheckOutDTOs = checkInOutService.getAllEmployeesCheckInCheckOutByDateRange(startDate, endDate);
        return checkInCheckOutDTOs;
    }

//    @GetMapping("/lateCheckIns")
//    public List<EmployeeCheckInCheckOutDTO> getLateCheckInsByMonth(@RequestParam("month") int month, @RequestParam("checkInTimeThreshold") @DateTimeFormat(pattern = "HH:mm") LocalTime checkInTimeThreshold) {
//        return checkInOutService.getLateCheckInsByMonth(month, checkInTimeThreshold);
//    }


}
