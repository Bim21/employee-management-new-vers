package com.ncc.service;

import com.ncc.constants.MessageConstant;
import com.ncc.dto.*;
import com.ncc.entity.ERole;
import com.ncc.entity.Employee;
import com.ncc.entity.EmployeeRole;
import com.ncc.exception.NotFoundException;
import com.ncc.mapper.EmployeeRequestMapper;
import com.ncc.repository.ICheckInOutRepository;
import com.ncc.repository.IEmployeeRepository;
import com.ncc.repository.IEmployeeRoleRepository;
import com.ncc.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {
    private final IEmployeeRepository employeeRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final IRoleRepository roleRepository;
    private final IEmployeeRoleRepository employeeRoleRepository;
//    private final EmployeeRequestMapper employeeRequestMapper;
    @PostConstruct
    public void init(){
        System.out.println("EmployeeService được khởi tạo.");
    }

    @PreDestroy
    public void cleanup(){
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
        for (EmployeeDTO employeeDTO : employeeDTOList){
            if(emailListGet.isEmpty() || !emailListGet.contains(employeeDTO.getEmail())){
                Employee employee = mapper.map(employeeDTO, Employee.class);
                employee.setUserName(employeeDTO.getEmail().substring(0, employeeDTO.getEmail().indexOf("@")));
                employee.setPassword(passwordEncoder.encode(employeeRequestDTO.getPassword()));
                String employeeCode = generateEmployeeCode();
                employee.setEmployeeCode(Integer.valueOf(employeeCode));
                employee = employeeRepository.save(employee);
                List<EmployeeRole> employeeRoles = new ArrayList<>();
                employeeRequestDTO.setRoleIds(Arrays.asList()); // Gán danh sách các roleId vào
                for(int roleId : employeeRequestDTO.getRoleIds()){
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
        for(Employee employee: employeeListGet){
            if(!emailDTOList.contains(employee.getEmail())){
                employee.setActive(false);
                employeeRepository.save(employee);
            }
        }
            return getEmployeeResponseDTOS(employeeList);
    }
    private List<EmployeeResponseDTO> getEmployeeResponseDTOS(List<Employee> employeeList){
        List<EmployeeResponseDTO> employeeResponseDTOList = new ArrayList<>();
        for (Employee employee: employeeList) {
            EmployeeResponseDTO employeeResponseDTO = mapper.map(employee, EmployeeResponseDTO.class);
            employeeResponseDTO.setFullName(employee.getLastName() + " " + employee.getFirstName());
            List<ERole> roleNames = new ArrayList<>();
            for (EmployeeRole employeeRole: employee.getEmployeeRoles()) {
                ERole roleName = employeeRole.getRole().getRoleName();
                roleNames.add(roleName);
            }
            employeeResponseDTO.setRoleNames(roleNames);
            employeeResponseDTOList.add(employeeResponseDTO);
        }
        return employeeResponseDTOList;
    }
    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) {
        String employeeCode = generateEmployeeCode();
        Employee employee = mapper.map(employeeRequestDTO, Employee.class);
        employee.setUserName(employee.getEmail().substring(0, employeeRequestDTO.getEmail().indexOf("@")));
        employee.setPassword(passwordEncoder.encode(employeeRequestDTO.getPassword()));
        employee.setEmployeeCode(Integer.valueOf(employeeCode));
        Employee saveEmployee = employeeRepository.save(employee);
        EmployeeResponseDTO saveEmployeeDTO = mapper.map(saveEmployee, EmployeeResponseDTO.class);
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
    public void saveUser(List<Employee> employees) {
        employeeRepository.saveAll(employees);
    }

    @Override
    public List<EmployeeResponseDTO> searchEmployeesByName(String keyword) {
        List<Employee> employees = employeeRepository.findByUserNameContainingIgnoreCase(keyword);
        return employees.stream()
                .map(employee -> mapper.map(employee, EmployeeResponseDTO.class))
                .collect(Collectors.toList());
    }

    private String generateEmployeeCode() {
        Random random = new Random();
        int code = random.nextInt(9000) + 1000;
        return String.valueOf(code);
    }
}
