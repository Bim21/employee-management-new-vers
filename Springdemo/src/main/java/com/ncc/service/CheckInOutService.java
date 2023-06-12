package com.ncc.service;

import com.ncc.constants.MessageConstant;
import com.ncc.dto.CheckInOutDTO;
import com.ncc.dto.EmployeeCheckInCheckOutDTO;
import com.ncc.dto.EmployeeResponseDTO;
import com.ncc.entity.CheckInOut;
import com.ncc.entity.Employee;
import com.ncc.exception.CheckInException;
import com.ncc.exception.NotFoundException;
import com.ncc.repository.ICheckInOutRepository;
import com.ncc.repository.IEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CheckInOutService implements ICheckInOutService{

    private final ICheckInOutRepository checkInOutRepository;
    private final IEmployeeRepository employeeRepository;
    private final ModelMapper mapper;

    @PostConstruct
    public void init(){
        System.out.println("CheckInOutService được khởi tạo.");
    }

    @PreDestroy
    public void cleanup(){
        System.out.println("CheckInOutService bị hủy");
    }
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
        List<CheckInOutDTO> checkInOutDTOs = checkInOutRepository.findByEmployeeIdAndDateBetween(employee,startDate, endDate);
        return checkInOutDTOs;
    }

    @Override
    public List<CheckInOutDTO> getCheckInErrorsByEmployeeAndMonth(Integer employeeCode, YearMonth yearMonth) {
        Employee employee = employeeRepository.findByEmployeeCode(employeeCode);
        // Lấy danh sách lỗi checkin của nhân viên trong tháng
        List<CheckInOutDTO> checkInErrors = checkInOutRepository.findCheckInErrorsByEmployeeAndMonth(employee, yearMonth);
        // Chuyển đổi sang DTO và trả về
        return checkInErrors;
    }

    @Override
    public List<EmployeeCheckInCheckOutDTO> getAllEmployeesCheckInCheckOutByDateRange(LocalDate startDate, LocalDate endDate) {
        List<CheckInOut> checkInOuts = checkInOutRepository.findByDateBetween(startDate, endDate);

        Map<Employee, Map<LocalDate, CheckInOutDTO>> employeeCheckInOutMap = new HashMap<>();

        for (CheckInOut checkInOut : checkInOuts){
            Employee employee = checkInOut.getEmployee();
            LocalDate date = checkInOut.getDate();

            // Kiểm tra xem nhân viên đã tồn tại trong Map chưa
            if (!employeeCheckInOutMap.containsKey(employee)){
                employeeCheckInOutMap.put(employee, new HashMap<>());
            }

            CheckInOutDTO checkInOutDTO = new CheckInOutDTO();
            checkInOutDTO.setCheckInTime(checkInOut.getCheckInTime());
            checkInOutDTO.setCheckOutTime(checkInOut.getCheckOutTime());

            //Thêm thông tin checkin/checkout vào Map của nhân viên tương ứng
            employeeCheckInOutMap.get(employee).put(date, checkInOutDTO);
        }

        List<EmployeeCheckInCheckOutDTO> result = new ArrayList<>();

        for (Map.Entry<Employee, Map<LocalDate, CheckInOutDTO>> entry : employeeCheckInOutMap.entrySet()){
            Employee employee = entry.getKey();
            Map<LocalDate, CheckInOutDTO> checkInOutDTOMap = (Map<LocalDate, CheckInOutDTO>) entry.getKey();

            EmployeeCheckInCheckOutDTO dto = new EmployeeCheckInCheckOutDTO();
            dto.setEmployeeResponseDTO(EmployeeResponseDTO.fromEntity(employee));
            dto.setCheckInOutDTOMap(checkInOutDTOMap);

            result.add(dto);
        }
        return result;
    }

    @Override
    public List<EmployeeCheckInCheckOutDTO> getLateCheckInsByMonth(int month, LocalTime checkInTimeThreshold) {
        List<Employee> employees = employeeRepository.findAll();

        List<EmployeeCheckInCheckOutDTO> result = new ArrayList<>();
        for(Employee employee: employees){
            List<CheckInOut> lateCheckIns = checkInOutRepository.findLateCheckInsByMonth(employee, month, checkInTimeThreshold);
            if (!lateCheckIns.isEmpty()){
                EmployeeCheckInCheckOutDTO dto = new EmployeeCheckInCheckOutDTO();
                dto.setEmployeeResponseDTO(EmployeeResponseDTO.fromEntity(employee));
                dto.setCheckInOutDTOMap((Map<LocalDate, CheckInOutDTO>) lateCheckIns);

                result.add(dto);
            }
        }
        return result;
    }
}
