package com.nytimes.spg.poc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchService {

    public void doBatchJobBWork() throws InterruptedException {
        final long JOB_EXECUTION_SECONDS = 10; // 1 min

        log.info("Batch job B work is being done");
        for (int i=0; i<JOB_EXECUTION_SECONDS; i++) {
            Thread.sleep(1000); // Sleep 1 sec
        }
        log.info("Batch job B work is complete");
    }

    public void doBatchJobCWork() throws InterruptedException {
        final long JOB_EXECUTION_SECONDS = 5; // 1 min

        log.info("Batch job C work is being done");
        for (int i=0; i<JOB_EXECUTION_SECONDS; i++) {
            Thread.sleep(1000); // Sleep 1 sec
        }
        log.info("Batch job C work is complete");
    }
}
