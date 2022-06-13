package com.nytimes.spg.poc.spring.batch.deciders;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

@Component
public class JobEDecider implements JobExecutionDecider {
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        String status;
        if (!hasFileArrived()) {
            status = "FAILED";
        } else {
            status = "COMPLETED";
        }
        return new FlowExecutionStatus(status);
    }

    private boolean hasFileArrived() {
        return true;
    }
}
