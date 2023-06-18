package com.ncc.projection;

import java.time.LocalDateTime;

public interface CheckInOutProjection {
    LocalDateTime getCheckInTime();
    LocalDateTime getCheckOutTime();
}
