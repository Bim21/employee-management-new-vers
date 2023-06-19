package com.ncc.mapper;

import com.ncc.dto.EmployeeDTO;
import com.ncc.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.servlet.handler.MappedInterceptor;

@Mapper
public interface EmployeeMapper {

    @Mapping(source = "email", target = "userName")
    @Mapping(target = "employeeCode", ignore = true)
    @Mapping(target = "password", ignore = true)
    Employee toEntity(EmployeeDTO dto);
    EmployeeDTO toDTO(Employee entity);
}
