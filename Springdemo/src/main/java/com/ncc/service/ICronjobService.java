package com.ncc.service;

import com.ncc.dto.CronjobRequest;
import com.ncc.entity.Cronjob;

import java.util.List;

public interface ICronjobService {
    List<Cronjob> getAllCronjobs();
    Cronjob getCronjobById(Integer id);
    Cronjob saveCronjob(CronjobRequest cronjobRequest);
    void deleteCronjob(Integer id);
}
