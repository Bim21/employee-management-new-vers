package com.ncc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cronjob")
public class Cronjob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cron_expression")
    private String cronExpression;

    @Column(name = "job_command")
    private String jobCommand;

    @Column(name = "job_parameters")
    private String jobParameters;

    @Column(name = "employee_id")
    private Integer employeeId;
}
