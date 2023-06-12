package com.ncc.mapper;

import com.ncc.common.EntityMapper;
import com.ncc.dto.EmployeeRequestDTO;
import com.ncc.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeRequestMapper extends EntityMapper<EmployeeRequestDTO, Employee> {
}
