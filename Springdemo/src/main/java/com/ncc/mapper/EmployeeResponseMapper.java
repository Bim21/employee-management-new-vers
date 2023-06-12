package com.ncc.mapper;

import com.ncc.common.EntityMapper;
import com.ncc.dto.EmployeeResponseDTO;
import com.ncc.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeResponseMapper extends EntityMapper<EmployeeResponseDTO, Employee> {
}
