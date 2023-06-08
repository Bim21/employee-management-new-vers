package com.ncc.repository;

import com.ncc.entity.CheckInOut;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICheckInOutRepository extends JpaRepository<CheckInOut, Integer> {
}
