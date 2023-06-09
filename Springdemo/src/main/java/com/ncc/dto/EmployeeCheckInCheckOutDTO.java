package com.ncc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeCheckInCheckOutDTO {
    private EmployeeDTO employeeDTO;
    private Map<LocalDate, CheckInOutDTO> checkInOutDTOMap;
}
