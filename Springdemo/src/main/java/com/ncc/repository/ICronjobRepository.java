package com.ncc.repository;

import com.ncc.entity.Cronjob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICronjobRepository extends JpaRepository<Cronjob, Integer> {
}
