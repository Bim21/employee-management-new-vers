package com.ncc.dto;

import com.ncc.entity.ERole;
import com.ncc.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDTO {
    private int Id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String employeeCode;
    private List<ERole> roleNames;
    private List<CheckInOutDTO> checkInRecords;
    private List<CheckInOutDTO> checkOutRecords;

    public static EmployeeResponseDTO fromEntity(Employee employee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
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
