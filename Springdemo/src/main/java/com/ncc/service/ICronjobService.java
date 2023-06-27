package com.ncc.service;

import com.ncc.dto.CronjobRequest;
import com.ncc.entity.Cronjob;
import com.ncc.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface ICronjobService {
   Cronjob createCronjob(CronjobRequest cronjobRequest);
   void deleteCronjob(int cronJobId);

   Cronjob updateCronjob(Integer id, CronjobRequest cronjobRequest);

   List<Employee> getEmployeesWithMissingCheckInOut();

   Optional<Cronjob> getCronjobById(Integer id);
}
