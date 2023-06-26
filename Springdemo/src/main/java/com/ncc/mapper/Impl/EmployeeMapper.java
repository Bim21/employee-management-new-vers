package com.ncc.mapper.Impl;

import com.ncc.dto.EmployeeCheckInCheckOutDTO;
import com.ncc.dto.EmployeeDTO;
import com.ncc.entity.Employee;
import com.ncc.mapper.IEmployeeMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper implements IEmployeeMapper {
    @Override
    public Employee toEntity(EmployeeDTO dto) {
        if(dto == null){
            return null;
        }

        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());

        return employee;
    }

    @Override
    public EmployeeDTO toDTO(Employee entity) {
        if(entity == null){
            return null;
        }
        return null;
    }

}
