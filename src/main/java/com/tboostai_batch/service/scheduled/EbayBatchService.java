package com.tboostai_batch.service.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EbayBatchService implements JobExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(EbayBatchService.class);

    private final JobLauncher jobLauncher;
    private final Job ebayBatchJob;

    @Autowired
    public EbayBatchService(JobLauncher jobLauncher, @Qualifier("ebayBatchJob") Job ebayBatchJob) {
        this.jobLauncher = jobLauncher;
        this.ebayBatchJob = ebayBatchJob;
    }


    // 定时任务：每天午夜运行一次
    @Scheduled(fixedRate = 360000)
    public void runEbayJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(ebayBatchJob, params);
            logger.info("Job Execution Status: {}", jobExecution.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {}
}

