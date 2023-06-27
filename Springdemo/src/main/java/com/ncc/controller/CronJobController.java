package com.ncc.controller;

import com.ncc.dto.CronjobRequest;
import com.ncc.entity.Cronjob;
import com.ncc.exception.NotFoundException;
import com.ncc.service.ICronjobService;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cronjobs")
public class CronJobController {
    private final ICronjobService cronJobService;

    public CronJobController(ICronjobService cronJobService) {
        this.cronJobService = cronJobService;
    }

    @PostMapping
    public Cronjob createCronJob(@RequestBody CronjobRequest cronjobRequest) {
        // Kiểm tra và tạo dynamic cron job
        return cronJobService.createCronjob(cronjobRequest);
    }

    @DeleteMapping("/{cronjobId}")
    public void deleteCronJob(@PathVariable("cronjobId") int cronjobId) {
        // Xóa dynamic cron job
        cronJobService.deleteCronjob(cronjobId);
    }

//    @PutMapping("/{id}")
//    public Cronjob updateCronjob(@PathVariable Integer id, @RequestBody Cronjob cronjob) {
//        Optional<Cronjob> updatedCronjob = cronJobService.getCronjobById(id);
//        if (updatedCronjob != null) {
//            // Cập nhật các thuộc tính của cronjob
//
//            // ...
//
//            // Lấy JobKey từ công việc đã cập nhật
//            JobKey jobKey = JobKey.jobKey(updatedCronjob.getId().toString());
//
//            // Cập nhật cron trigger cho công việc
//            Trigger newTrigger = TriggerBuilder.newTrigger()
//                    .forJob(jobKey)
//                    .withIdentity(updatedCronjob.getId().toString() + "_trigger")
//                    .withSchedule(CronScheduleBuilder.cronSchedule(updatedCronjob.getCronExpression()))
//                    .build();
//
//            // Cập nhật cron job và cron trigger
//            cronJobService.updateCronjob(updatedCronjob, jobKey, newTrigger);
//
//            return updatedCronjob;
//        } else {
//            throw new NotFoundException("Cronjob not found");
//        }
//    }



}

