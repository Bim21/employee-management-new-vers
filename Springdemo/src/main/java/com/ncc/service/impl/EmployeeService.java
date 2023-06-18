package com.ncc.service.impl;

import com.ncc.constants.MessageConstant;
import com.ncc.dto.*;
import com.ncc.entity.CheckInOut;
import com.ncc.entity.ERole;
import com.ncc.entity.Employee;
import com.ncc.entity.EmployeeRole;
import com.ncc.exception.NotFoundException;
import com.ncc.repository.ICheckInOutRepository;
import com.ncc.repository.IEmployeeRepository;
import com.ncc.repository.IEmployeeRoleRepository;
import com.ncc.repository.IRoleRepository;
import com.ncc.service.IMailService;
import com.ncc.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.mail.MessagingException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Scope("prototype")
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {
    private final IEmployeeRepository employeeRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final IRoleRepository roleRepository;
    private final IEmployeeRoleRepository employeeRoleRepository;
    private final IMailService mailService;
    private final ICheckInOutRepository checkInOutRepository;

    //    private final EmployeeRequestMapper employeeRequestMapper;
    @PostConstruct
    public void init() {
        System.out.println("EmployeeService được khởi tạo.");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("EmployeeService bị hủy");
    }

    @Override
    public List<EmployeeResponseDTO> syncData(EmployeeRequestDTO employeeRequestDTO) {

        ResultDTO resultDTO = restTemplate.getForObject("/", ResultDTO.class);

        List<EmployeeDTO> employeeDTOList = resultDTO.getResult();
//        if (!roleRepository.getSetRoleByRoleId(employeeRequestDTO.getRoleIds()).isEmpty()) {
//            throw new NotFoundException(MessageConstant.ROLE_IS_NULL);
//        }
        List<Employee> employeeListGet = employeeRepository.findAll();
        List<String> emailListGet = new ArrayList<>();
        List<Employee> employeeList = new ArrayList<>();
        if (!employeeListGet.isEmpty()) {
            emailListGet = employeeListGet.stream()
                    .map(Employee::getEmail)
                    .collect(Collectors.toList());
        }
        List<String> emailDTOList = employeeDTOList.stream()
                .map(EmployeeDTO::getEmail)
                .collect(Collectors.toList());
        for (EmployeeDTO employeeDTO : employeeDTOList) {
            int numberOfEmployees = employeeList.size();
            int numberOfFakeCheckIns = numberOfEmployees;
            if (emailListGet.isEmpty() || !emailListGet.contains(employeeDTO.getEmail())) {
                Employee employee = mapper.map(employeeDTO, Employee.class);
                employee.setUserName(employeeDTO.getEmail().substring(0, employeeDTO.getEmail().indexOf("@")));
                employee.setPassword(passwordEncoder.encode(employeeRequestDTO.getPassword()));
                String employeeCode = generateEmployeeCode();
                employee.setEmployeeCode(Integer.valueOf(employeeCode));
                employee = employeeRepository.save(employee);
//                createFakeCheckInsAndCheckOuts(employee.getId(), numberOfFakeCheckIns);
                List<EmployeeRole> employeeRoles = new ArrayList<>();
                employeeRequestDTO.setRoleIds(Arrays.asList()); // Gán danh sách các roleId vào
                for (int roleId : employeeRequestDTO.getRoleIds()) {
                    EmployeeRole employeeRole = new EmployeeRole();
                    employeeRole.setEmployee(employeeRepository.getById(employee.getId()));
                    employeeRole.setRole(roleRepository.getById(roleId));
                    employeeRoles.add(employeeRole);
                }
                employee.setEmployeeRoles(employeeRoles);
                employeeList.add(employee);
                employeeRoles = employeeRoleRepository.saveAll(employeeRoles);
            }
        }
        employeeRepository.saveAll(employeeList);
        for (Employee employee : employeeListGet) {
            if (!emailDTOList.contains(employee.getEmail())) {
                employee.setActive(false);
                employeeRepository.save(employee);
            }
        }
        return getEmployeeResponseDTOS(employeeList);
    }

    private List<EmployeeResponseDTO> getEmployeeResponseDTOS(List<Employee> employeeList) {
        List<EmployeeResponseDTO> employeeResponseDTOList = new ArrayList<>();
        for (Employee employee : employeeList) {
            EmployeeResponseDTO employeeResponseDTO = mapper.map(employee, EmployeeResponseDTO.class);
            employeeResponseDTO.setFullName(employee.getLastName() + " " + employee.getFirstName());
            List<ERole> roleNames = new ArrayList<>();
            for (EmployeeRole employeeRole : employee.getEmployeeRoles()) {
                ERole roleName = employeeRole.getRole().getRoleName();
                roleNames.add(roleName);
            }
//            employeeResponseDTO.setRoleNames(roleNames);
            employeeResponseDTOList.add(employeeResponseDTO);
        }
        return employeeResponseDTOList;
    }

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) throws MessagingException {
        String employeeCode = generateEmployeeCode();
        Employee employee = mapper.map(employeeRequestDTO, Employee.class);
        employee.setUserName(employee.getEmail().substring(0, employeeRequestDTO.getEmail().indexOf("@")));
        employee.setPassword(passwordEncoder.encode(employeeRequestDTO.getPassword()));
        employee.setEmployeeCode(Integer.valueOf(employeeCode));
        Employee saveEmployee = employeeRepository.save(employee);
        EmployeeResponseDTO saveEmployeeDTO = mapper.map(saveEmployee, EmployeeResponseDTO.class);
//        mailService.sendHtmlMessage(employee, employee.getPassword());
        mailService.sendWelcomeEmail(employee);
        return saveEmployeeDTO;
    }

    @Override
    public EmployeeResponseDTO updateEmployee(EmployeeRequestDTO employeeRequestDTO) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeRequestDTO.getId());
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            mapper.map(employeeRequestDTO, employee);
            Employee updateEmployee = employeeRepository.save(employee);
            EmployeeResponseDTO updateEmployeeDTO = mapper.map(updateEmployee, EmployeeResponseDTO.class);
            return updateEmployeeDTO;
        } else {
            throw new NotFoundException(MessageConstant.EMPLOYEE_IS_NULL);
        }
    }

    @Override
    public void deleteEmployeeById(int id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            employeeRepository.delete(optionalEmployee.get());
        } else {
            throw new NotFoundException(MessageConstant.EMPLOYEE_IS_NULL);
        }
    }

    @Override
    public List<EmployeeResponseDTO> searchEmployeesByName(String keyword) {
        List<Employee> employees = employeeRepository.findByUserNameContainingIgnoreCase(keyword);
        return employees.stream()
                .map(employee -> mapper.map(employee, EmployeeResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponseDTO> employeeDTOs = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeResponseDTO dto = EmployeeResponseDTO.fromEntity(employee);
            employeeDTOs.add(dto);
        }

        return employeeDTOs;
    }


    private String generateEmployeeCode() {
        Random random = new Random();
        int code = random.nextInt(9000) + 1000;
        return String.valueOf(code);
    }
    public void createFakeCheckInsAndCheckOuts(int employeeId, int numberOfFakeCheckIns) {
        Employee employee = employeeRepository.getById(employeeId);
        for (int i = 0; i < numberOfFakeCheckIns; i++) {
            LocalDate fakeDate = generateFakeDate(employeeId);
            LocalDateTime fakeCheckInTime = generateFakeCheckInTime(fakeDate);
            LocalDateTime fakeCheckOutTime = generateFakeCheckOutTime(fakeCheckInTime);

            // Tạo bản ghi check-in và check-out giả cho nhân viên
            CheckInOut checkIn = new CheckInOut();
            checkIn.setEmployee(employee);
            checkIn.setDate(fakeCheckInTime.toLocalDate());
            checkIn.setCheckInTime(fakeCheckInTime);

            CheckInOut checkOut = new CheckInOut();
            checkOut.setEmployee(employee);
            checkOut.setDate(fakeCheckOutTime.toLocalDate());
            checkOut.setCheckOutTime(fakeCheckOutTime);

            // Lưu bản ghi check-in và check-out vào cơ sở dữ liệu
            checkInOutRepository.save(checkIn);
            checkInOutRepository.save(checkOut);
        }
    }

    private LocalDate generateFakeDate(int employeeId) {
        // Sử dụng ID của nhân viên để tạo ngày giả
        LocalDate baseDate = LocalDate.of(2022, 1, 1); // Ngày cơ sở
        int daysToAdd = employeeId % 365; // Số ngày cần thêm dựa trên ID của nhân viên
        return baseDate.plusDays(daysToAdd);
    }
    private LocalDateTime generateFakeCheckInTime(LocalDate date) {
        // Tạo ngẫu nhiên giờ checkin trong khoảng thời gian từ 8:00 đến 9:00
        LocalTime fakeCheckInTime = LocalTime.of(8, 0)
                .plusMinutes(new Random().nextInt(60));

        return LocalDateTime.of(date, fakeCheckInTime);
    }

    private LocalDateTime generateFakeCheckOutTime(LocalDateTime checkInTime) {
        // Tạo ngẫu nhiên giờ checkout sau giờ checkin từ 1 đến 9 tiếng
        LocalTime fakeCheckOutTime = checkInTime.toLocalTime()
                .plusHours(1 + new Random().nextInt(9));

        return LocalDateTime.of(checkInTime.toLocalDate(), fakeCheckOutTime);
    }

    @Override
    public List<CheckInOutDTO> getCheckInOutsByEmployeeId(int employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        if (employee != null) {
            List<CheckInOut> checkInOuts = employee.getCheckInOuts();

            List<CheckInOutDTO> checkInOutDTOs = new ArrayList<>();
            for (CheckInOut checkInOut : checkInOuts) {
                CheckInOutDTO dto = new CheckInOutDTO();
                dto.setEmployeeId(checkInOut.getId());
                dto.setCheckInTime(checkInOut.getCheckInTime());
                dto.setCheckOutTime(checkInOut.getCheckOutTime());
                // ...
                checkInOutDTOs.add(dto);
            }

            return checkInOutDTOs;
        } else {
            throw new NotFoundException("Employee not found with ID: " + employeeId);
        }
    }

    @Override
    public List<EmployeeResponseDTO> getEmployeesWithCheckInOuts(LocalDate startDate, LocalDate endDate) {
        final LocalDate start = startDate != null ? startDate : LocalDate.now().with(DayOfWeek.MONDAY);
        final LocalDate end = endDate != null ? endDate : LocalDate.now();

        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponseDTO> employeeDTOs = new ArrayList<>();

        for (Employee employee : employees) {
            List<CheckInOut> checkInOuts = employee.getCheckInOuts().stream()
                    .filter(checkInOut -> checkInOut.getDate() != null &&
                            checkInOut.getDate().isAfter(start.minusDays(1)) &&
                                    checkInOut.getDate().isBefore(end.plusDays(1))
                    )
                    .collect(Collectors.toList());

            EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(employee, checkInOuts);
            employeeDTOs.add(employeeResponseDTO);
        }

        return employeeDTOs;
    }


    @Override
    public List<Employee> getEmployeesWithoutCheckInOut() {
        return employeeRepository.getEmployeesWithoutCheckInOut();
    }
}



