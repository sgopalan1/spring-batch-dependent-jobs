package com.nytimes.spg.poc.integration.channel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class BatchJobChannels {
    @Autowired
    private Job jobB;
    @Autowired
    private Job jobC;
    @Autowired
    private JobLauncher jobLauncher;

    @ServiceActivator(inputChannel = "TEMPjobAStatusReportingChannel")
    public Message<String> consumeJobBMessage(Message<String> message) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("consumeJobBMessage -> Received message: " + message.getPayload());
        JobParameters jobParameters = new JobParametersBuilder()
             .addDate("rundate", new Date())
             .addString("messagePayload", message.getPayload())
             .toJobParameters();

        jobLauncher.run(jobB, jobParameters);
        return null;
    }

    @ServiceActivator(inputChannel = "TEMPjobBStatusReportingChannel")
    public Message<String> consumeJobCMessage(Message<String> message) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("consumeJobCMessage -> Received message: " + message.getPayload());
        JobParameters jobParameters = new JobParametersBuilder()
             .addDate("rundate", new Date())
             .addString("messagePayload", message.getPayload())
             .toJobParameters();

        jobLauncher.run(jobC, jobParameters);
        return null;
    }
}
