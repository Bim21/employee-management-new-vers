package com.ncc.service.impl;

import com.ncc.dto.CronjobRequest;
import com.ncc.entity.Cronjob;
import com.ncc.entity.Employee;
import com.ncc.repository.ICheckInOutRepository;
import com.ncc.repository.ICronjobRepository;
import com.ncc.repository.IEmployeeRepository;
import com.ncc.scheduled.EmailReminderJob;
import com.ncc.service.ICronjobService;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CronJobService implements ICronjobService {
    private final ICronjobRepository cronjobRepository;
    private final SchedulerFactoryBean schedulerFactoryBean;

    private final IEmployeeRepository employeeRepository;

    private final ICheckInOutRepository checkInOutRepository;
    @Override
    public Cronjob createCronjob(CronjobRequest cronjobRequest) {
        try {
            if (CronExpression.isValidExpression(cronjobRequest.getCronExpression())) {
                Cronjob cronjob = new Cronjob();
                cronjob.setCronExpression(cronjobRequest.getCronExpression());
                cronjobRepository.save(cronjob);

                Scheduler scheduler = schedulerFactoryBean.getScheduler();
                JobDetail jobDetail = buildJobDetail(cronjob);
                Trigger trigger = buildCronTrigger(cronjob);
                scheduler.scheduleJob(jobDetail, trigger);

                return cronjob;
            } else {
                throw new IllegalArgumentException("Invalid cron expression");
            }
        } catch (SchedulerException e) {
            throw new RuntimeException("Failed to create cron job", e);
        }
    }

    @Override
    public void deleteCronjob(int cronJobId) {
        try {
            Cronjob cronjob = cronjobRepository.getById(cronJobId);
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.unscheduleJob(new TriggerKey(cronjob.getId().toString() + "_trigger"));
            scheduler.deleteJob(new JobKey(cronjob.getId().toString()));
            cronjobRepository.deleteById(cronJobId);
        } catch (SchedulerException e) {
            throw new RuntimeException("Failed to delete cron job", e);
        }
    }

    @Override
    public Cronjob updateCronjob(Integer id, CronjobRequest cronjobRequest) {
        Optional<Cronjob> optionalCronjob = cronjobRepository.findById(id);
        if (optionalCronjob.isPresent()) {
            Cronjob cronjob = optionalCronjob.get();
            if (CronExpression.isValidExpression(cronjobRequest.getCronExpression())) {
                cronjob.setCronExpression(cronjobRequest.getCronExpression());
                cronjobRepository.save(cronjob);

                try {
                    Scheduler scheduler = schedulerFactoryBean.getScheduler();
                    JobKey jobKey = JobKey.jobKey(String.valueOf(cronjob.getId()));
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);

                    if (jobDetail != null) {
                        // Cập nhật cron trigger cho công việc
                        Trigger newTrigger = TriggerBuilder.newTrigger()
                                .forJob(jobKey)
                                .withIdentity(cronjob.getId().toString() + "_trigger")
                                .withSchedule(CronScheduleBuilder.cronSchedule(cronjob.getCronExpression()))
                                .build();

                        scheduler.rescheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()), newTrigger);
                    }
                } catch (SchedulerException e) {
                    throw new RuntimeException("Failed to update cronjob", e);
                }
            } else {
                throw new IllegalArgumentException("Invalid cron expression");
            }
            return cronjob;
        } else {
            throw new NoSuchElementException("Cronjob not found");
        }
    }

    @Override
    public List<Employee> getEmployeesWithMissingCheckInOut() {
        LocalDate currentDate = LocalDate.now();
        List<Employee> employees = employeeRepository.findAll();
        List<Employee> employeesWithMissingCheckInOut = new ArrayList<>();

        for (Employee employee : employees) {
            boolean hasCheckInOutToday = checkInOutRepository.existsByEmployeeAndDate(employee, currentDate);
            if (!hasCheckInOutToday) {
                employeesWithMissingCheckInOut.add(employee);
            }
        }

        return employeesWithMissingCheckInOut;
    }

    @Override
    public Optional<Cronjob> getCronjobById(Integer id) {
        return cronjobRepository.findById(id);
    }

    private JobDetail buildJobDetail(Cronjob cronjob) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("employeeId", cronjob.getEmployeeId());

        return JobBuilder.newJob(EmailReminderJob.class)
                .withIdentity(cronjob.getId().toString())
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildCronTrigger(Cronjob cronjob) {
        return TriggerBuilder.newTrigger()
                .forJob(cronjob.getId().toString())
                .withIdentity(cronjob.getId().toString() + "_trigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(cronjob.getCronExpression()))
                .build();
    }


}


// TODO: Dynamic Cron not done