package com.ncc.dto;

import com.ncc.entity.CheckInOut;
import com.ncc.entity.ERole;
import com.ncc.entity.Employee;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDTO {
    private int Id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String employeeCode;
//    private List<ERole> roleNames;
    private List<CheckInOutDTO> checkInRecords;
    private List<CheckInOutDTO> checkOutRecords;

    public EmployeeResponseDTO(Employee employee, List<CheckInOut> checkInOuts) {
    }

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
