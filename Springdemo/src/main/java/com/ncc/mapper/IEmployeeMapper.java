package com.ncc.mapper;

import com.ncc.dto.EmployeeDTO;
import com.ncc.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IEmployeeMapper {
//    IEmployeeMapper INSTANCE = Mappers.getMapper(IEmployeeMapper.class);

    @Mapping(target = "email")
    @Mapping(target = "employeeCode", ignore = true)
    @Mapping(target = "password", ignore = true)
    Employee toEntity(EmployeeDTO dto);
    EmployeeDTO toDTO(Employee entity);
}
