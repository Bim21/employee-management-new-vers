package com.ncc.service;

import com.ncc.constants.MessageConstant;
import com.ncc.dto.CheckInOutDTO;
import com.ncc.entity.CheckInOut;
import com.ncc.entity.Employee;
import com.ncc.exception.CheckInException;
import com.ncc.exception.NotFoundException;
import com.ncc.repository.ICheckInOutRepository;
import com.ncc.repository.IEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckInOutService implements ICheckInOutService{

    private final ICheckInOutRepository checkInOutRepository;
    private final IEmployeeRepository employeeRepository;
    private final ModelMapper mapper;
    @Override
    public CheckInOutDTO checkIn(Integer employeeCode) {
        Employee employee = employeeRepository.findByEmployeeCode(employeeCode);
        if(employee == null){
            throw new NotFoundException(MessageConstant.EMPLOYEE_IS_NULL);
        }

        boolean hasCheckInOut = checkInOutRepository.existsByEmployeeAndCheckOutTimeIsNull(employee);
        if (hasCheckInOut){
            throw new CheckInException(MessageConstant.CHECK_IN_SUCCESSFULLY);
        }

        CheckInOut checkInOut = new CheckInOut();
        checkInOut.setEmployee(employee);
        checkInOut.setCheckInTime(LocalDateTime.now());

        CheckInOut saveCheckInOut = checkInOutRepository.save(checkInOut);
        return mapper.map(saveCheckInOut, CheckInOutDTO.class);
    }

    @Override
    public CheckInOutDTO checkOut(Integer employeeCode) {
        Employee employee = employeeRepository.findByEmployeeCode(employeeCode);
        if(employee == null){
            throw new NotFoundException(MessageConstant.EMPLOYEE_IS_NULL);
        }

        CheckInOut checkInOut = checkInOutRepository.findByEmployeeAndCheckOutTimeIsNull(employee);
        if(checkInOut == null){
            throw new CheckInException(MessageConstant.CHECK_OUT_SUCCESSFULLY);
        }

        checkInOut.setCheckOutTime(LocalDateTime.now());

        CheckInOut saveCheckInOut = checkInOutRepository.save(checkInOut);
        return mapper.map(saveCheckInOut, CheckInOutDTO.class);
    }

    @Override
    public List<CheckInOutDTO> getCheckInRecordsByEmployeeAndDateRange(Integer employeeCode, LocalDate startDate, LocalDate endDate) {
        Employee employee = employeeRepository.findByEmployeeCode(employeeCode);
        List<CheckInOutDTO> checkInOutDTOs = checkInOutRepository.findByEmployeeIdAndDateBetween(employee.getId(),startDate, endDate);
        return convertToDTOs(checkInOutDTOs);
    }

    private List<CheckInOutDTO> convertToDTOs(List<CheckInOut> checkInOuts){
        List<CheckInOutDTO> dtos = new ArrayList<>();
        for(CheckInOut checkInOut : checkInOuts){
            CheckInOutDTO dto = new CheckInOutDTO();
            //Sao chép các thuộc tính từ CheckinRecord sang CheckInOut sang CheckInOutDTO
            dto.setId(checkInOut.getId());
            dto.setCheckInTime(checkInOut.getCheckInTime());
            dto.setCheckOutTime(checkInOut.getCheckOutTime());
            // Thêm dto vào danh sách
            dtos.add(dto);
        }
        return dtos;
    }
}
