package com.ncc.scheduled;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Map;

public class CronJobExecutor implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String jobCommand = jobDataMap.getString("jobCommand");
        Map<String, Object> jobParameters = (Map<String, Object>) jobDataMap.get("jobParameter");
        System.out.println("Dynamic cron is running");
    }
}
