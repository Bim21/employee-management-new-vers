package com.ncc.dto;

import com.ncc.entity.Address;
import com.ncc.entity.CheckInOut;
import com.ncc.entity.ERole;
import com.ncc.entity.Employee;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private Address addresses;
    private String employeeCode;
//    private List<ERole> roleNames;
    private List<CheckInOutDTO> checkInRecords;
    private List<CheckInOutDTO> checkOutRecords;

    public EmployeeResponseDTO(Employee employee, List<CheckInOut> checkInOuts) {
        this.Id = employee.getId();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.email = employee.getEmail();
        this.employeeCode = String.valueOf(employee.getEmployeeCode());
        this.checkInRecords = new ArrayList<>();
        this.checkOutRecords = new ArrayList<>();

        for (CheckInOut checkInOut : checkInOuts) {
            if (checkInOut.getCheckInTime() != null) {
                LocalDateTime checkInTime = checkInOut.getCheckInTime();
                CheckInOutDTO checkInOutDTO = new CheckInOutDTO();
                checkInOutDTO.setCheckInTime(checkInTime);
                this.checkInRecords.add(checkInOutDTO);
            }
            if (checkInOut.getCheckOutTime() != null) {
                LocalDateTime checkOutTime = checkInOut.getCheckOutTime();
                CheckInOutDTO checkInOutDTO = new CheckInOutDTO();
                checkInOutDTO.setCheckOutTime(checkOutTime);
                this.checkOutRecords.add(checkInOutDTO);
            }
        }
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
