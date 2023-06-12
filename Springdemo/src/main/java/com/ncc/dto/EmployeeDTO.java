package com.ncc.dto;

import com.ncc.entity.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String employeeCode;
    private List<CheckInOutDTO> checkInRecords;
    private List<CheckInOutDTO> checkOutRecords;

    public static EmployeeDTO fromEntity(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        // Sao chép các thuộc tính từ Entity sang DTO
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setEmployeeCode(dto.getEmployeeCode());

        // ...

        return dto;
    }
}
