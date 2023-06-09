package com.ncc.service.impl;

import com.ncc.constants.MessageConstant;
import com.ncc.dto.CheckInOutDTO;
import com.ncc.dto.EmployeeCheckInCheckOutDTO;
import com.ncc.dto.EmployeeResponseDTO;
import com.ncc.entity.CheckInOut;
import com.ncc.entity.Employee;
import com.ncc.exception.CheckInException;
import com.ncc.exception.CustomExceptionHandler;
import com.ncc.exception.NotFoundException;
import com.ncc.projection.CheckInOutProjection;
import com.ncc.repository.ICheckInOutRepository;
import com.ncc.repository.IEmployeeRepository;
import com.ncc.service.ICheckInOutService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.transaction.Transactional;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CheckInOutService implements ICheckInOutService {

    private final ICheckInOutRepository checkInOutRepository;
    private final IEmployeeRepository employeeRepository;
    private final ModelMapper mapper;

    @PostConstruct
    public void init() {
        System.out.println("CheckInOutService được khởi tạo.");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("CheckInOutService bị hủy");
    }

    @Transactional
    @Override
    public CheckInOutDTO checkIn(Integer employeeCode) {
        Employee employee = employeeRepository.findByEmployeeCode(employeeCode);
        if (employee == null) {
            throw new NotFoundException(MessageConstant.EMPLOYEE_IS_NULL);
        }

        boolean hasCheckInOut = checkInOutRepository.existsByEmployeeAndCheckOutTimeIsNull(employee);
        if (hasCheckInOut) {
            throw new CheckInException(MessageConstant.CHECK_IN_SUCCESSFULLY);
        }
        CheckInOut checkInOut = new CheckInOut();
        checkInOut.setEmployee(employee);
        checkInOut.setCheckInTime(LocalDateTime.now());

        CheckInOut saveCheckInOut = checkInOutRepository.save(checkInOut);
        return mapper.map(saveCheckInOut, CheckInOutDTO.class);
    }

    @Transactional
    @Override
    public CheckInOutDTO checkOut(Integer employeeCode) {
        Employee employee = employeeRepository.findByEmployeeCode(employeeCode);
        if (employee == null) {
            throw new NotFoundException(MessageConstant.EMPLOYEE_IS_NULL);
        }

        CheckInOut checkInOut = checkInOutRepository.findByEmployeeAndCheckOutTimeIsNull(employee);
        if (checkInOut == null) {
            throw new CheckInException(MessageConstant.CHECK_OUT_SUCCESSFULLY);
        }
        if(checkInOut.isError()){
            throw new CheckInException(MessageConstant.CHECK_OUT_FAILED);
        }
        checkInOut.setCheckOutTime(LocalDateTime.now());

        CheckInOut saveCheckInOut = checkInOutRepository.save(checkInOut);
        return mapper.map(saveCheckInOut, CheckInOutDTO.class);
    }

    @Override
    public List<CheckInOutProjection> getCheckInOutByEmployeeAndDateRange(Integer employeeId, LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            startDate = LocalDate.now().with(DayOfWeek.MONDAY); // Tuần hiện tại từ thứ 2
        }
        if (endDate == null) {
            endDate = startDate.plusDays(6); // Tuần hiện tại đến chủ nhật
        }
        return checkInOutRepository.findCheckInOutByEmployeeAndDateBetween(employeeId, startDate, endDate);
    }

    @Override
    public List<CheckInOut> getErrorCheckInsByEmployeeAndMonth(Integer employeeId, int year, int month) {
        Employee employee = new Employee();
        employee.setId(employeeId);
        return checkInOutRepository.findErrorCheckInsByEmployeeAndMonth(employee, year, month);
    }

//    @Override
//    public List<CheckInOut> getCheckInOutListByEmployeeIdAndDateRange(Integer employeeId, LocalDate startDate, LocalDate endDate) {
//        return checkInOutRepository.findByEmployeeAndDateBetweenNative(employeeId, startDate, endDate);
//    }

}
