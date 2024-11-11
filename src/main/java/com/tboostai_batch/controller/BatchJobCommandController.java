package com.tboostai_batch.controller;

import com.tboostai_batch.service.scheduled.EbayRetrieveVehicleDataBatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/command")
public class BatchJobCommandController {

    private final EbayRetrieveVehicleDataBatchService ebayRetrieveVehicleDataBatchService;
    private static final Logger logger = LoggerFactory.getLogger(BatchJobCommandController.class);

    public BatchJobCommandController(EbayRetrieveVehicleDataBatchService ebayRetrieveVehicleDataBatchService) {
        this.ebayRetrieveVehicleDataBatchService = ebayRetrieveVehicleDataBatchService;
    }

    @PostMapping("/manual-run-ebay-batch-job")
    private ResponseEntity<String> manualRunEbayBatchJob() {

        logger.info("BatchJobCommandController - Manual run ebay batch job.");
        ebayRetrieveVehicleDataBatchService.manualRunEbayJob();
        return ResponseEntity.accepted().body("Started Manual Ebay Batch Job.");
    }
}
