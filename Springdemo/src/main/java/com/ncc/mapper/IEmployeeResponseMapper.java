package com.ncc.mapper;

import com.ncc.common.EntityMapper;
import com.ncc.dto.EmployeeDTO;
import com.ncc.dto.EmployeeRequestDTO;
import com.ncc.dto.EmployeeResponseDTO;
import com.ncc.entity.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IEmployeeResponseMapper {
    Employee toEntity(EmployeeResponseDTO employeeResponseDTO);

    EmployeeResponseDTO toDTO(Employee employee);
    List<EmployeeResponseDTO> toDTOs(List<Employee> employees);

    Employee toEntity(EmployeeRequestDTO employeeRequestDTO);
}
