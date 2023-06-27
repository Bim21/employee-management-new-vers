package com.ncc.service.impl;

import com.ncc.constants.MessageConstant;
import com.ncc.dto.CronjobRequest;
import com.ncc.entity.Cronjob;
import com.ncc.exception.CronJobException;
import com.ncc.repository.ICronjobRepository;
import com.ncc.scheduled.CronJobExecutor;
import com.ncc.service.ICronjobService;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerAccessor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CronjobService implements ICronjobService {
    private final ICronjobRepository cronjobRepository;
    private final SchedulerFactoryBean schedulerFactoryBean;
    @Override
    public List<Cronjob> getAllCronjobs() {

        return cronjobRepository.findAll();
    }

    @Override
    public Cronjob getCronjobById(Integer id) {
        return cronjobRepository.getById(id);
    }

    @Override
    public Cronjob saveCronjob(CronjobRequest cronjobRequest) {
        try {
            if (CronExpression.isValidExpression(cronjobRequest.getCronExpression())){
                Cronjob cronjob = new Cronjob();
                cronjob.setCronExpression(cronjobRequest.getCronExpression());
                cronjobRepository.save(cronjob);

                Scheduler scheduler = schedulerFactoryBean.getScheduler();
                JobDetail jobDetail = buildJobDetail(cronjob);
                Trigger trigger = buidCronTrigger(cronjob);
                scheduler.scheduleJob(jobDetail, trigger);

            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    @Override
    public void deleteCronjob(Integer id) {
        cronjobRepository.deleteById(id);
    }
    private JobDetail buildJobDetail(Cronjob cronjob){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobCommand", cronjob.getJobCommand());
        jobDataMap.put("jobParameters", cronjob.getJobParameters());

        return JobBuilder.newJob(CronJobExecutor.class)
                .withIdentity(cronjob.getId().toString())
                .setJobData(jobDataMap)
                .storeDurably().build();
    }
    private Trigger buidCronTrigger(Cronjob cronjob){
        return TriggerBuilder.newTrigger()
                .forJob(cronjob.getId().toString())
                .withIdentity(cronjob.getId().toString() + "_trigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(cronjob.getCronExpression()))
                .build();
    }
}

// TODO: Dynamic Cron not done