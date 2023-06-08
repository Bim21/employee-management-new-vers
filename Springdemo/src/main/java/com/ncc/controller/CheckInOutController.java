package com.ncc.controller;

import com.ncc.dto.CheckInOutDTO;
import com.ncc.service.CheckInOutService;
import com.ncc.service.ICheckInOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
