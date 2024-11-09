package com.tboostai_batch.controller;

import com.tboostai_batch.service.scheduled.EbayRetrieveVehicleDataBatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/command")
public class BatchJobCommandController {

    private final EbayRetrieveVehicleDataBatchService ebayRetrieveVehicleDataBatchService;
    private static final Logger logger = LoggerFactory.getLogger(BatchJobCommandController.class);

    public BatchJobCommandController(EbayRetrieveVehicleDataBatchService ebayRetrieveVehicleDataBatchService) {
        this.ebayRetrieveVehicleDataBatchService = ebayRetrieveVehicleDataBatchService;
    }

    @PostMapping("/manual-run-ebay-batch-job")
    private void manualRunEbayBatchJob() {

        logger.info("BatchJobCommandController - Manual run ebay batch job.");
        ebayRetrieveVehicleDataBatchService.manualRunEbayJob();
    }
}