package com.tboostai_batch.service.scheduled;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EbayRetrieveVehicleDataBatchService implements IEbayRetrieveVehicleDataBatchService, JobExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(EbayRetrieveVehicleDataBatchService.class);

    @Value("${ebay.job.scheduled}") // job scheduled switch
    private boolean ebayJobEnabled;

    private final JobLauncher jobLauncher;
    private final Job ebayBatchJob;

    @Autowired
    public EbayRetrieveVehicleDataBatchService(JobLauncher jobLauncher, @Qualifier("ebayRetrieveVehicleDataBatchJob") Job ebayBatchJob) {
        this.jobLauncher = jobLauncher;
        this.ebayBatchJob = ebayBatchJob;
    }


    // Run every 2 hours
    @Scheduled(cron = "0 0 */2 * * *")
    @Override
    public void scheduledRunEbayJob() {
        if (ebayJobEnabled) {
            runEbayJob();
        } else {
            logger.info("EbayRetrieveVehicleDataBatchService - Ebay scheduled Job is disabled");
        }
    }

    @Async("asyncExecutor")
    public void manualRunEbayJob() {
        runEbayJob();
        logger.info("EbayRetrieveVehicleDataBatchService - Ebay batch Job was ran manually.");
    }

    private void runEbayJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(ebayBatchJob, params);
            logger.info("EbayRetrieveVehicleDataBatchService - Job Execution Status: {}", jobExecution.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }


    @Override
    public void beforeJob(@NotNull JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("EbayRetrieveVehicleDataBatchService - Batch job finished, status of the job is {}", jobExecution.getStatus());

        if (jobExecution.getStatus().isUnsuccessful() || jobExecution.getStatus() == BatchStatus.FAILED) {
            logger.info("EbayRetrieveVehicleDataBatchService - Batch job failed, rollback redis records");
        }

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("EbayRetrieveVehicleDataBatchService - Batch job completed");
        }
    }
}

