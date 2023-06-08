package com.ncc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CheckInOutDTO {
    private int id;
    private int employeeId;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private boolean isError;
}
