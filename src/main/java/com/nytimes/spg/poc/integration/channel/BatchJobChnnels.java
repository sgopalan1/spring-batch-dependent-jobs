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
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class BatchJobChnnels {
    @Autowired
    private Job myFirstJob;
    @Autowired
    private Job mySecondJob;
    @Autowired
    private JobLauncher jobLauncher;

    @ServiceActivator(inputChannel = "job1Channel")
    public Message<String> consumeJob1Message(Message<String> message) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("consumeJob1Message -> Received message from gateway : " + message.getPayload());
        JobParameters jobParameters = new JobParametersBuilder()
             .addDate("rundate", new Date())
             .addString("messagePayload", message.getPayload())
             .toJobParameters();

        jobLauncher.run(myFirstJob, jobParameters);
        return null;
    }

    @ServiceActivator(inputChannel = "job2Channel")
    public Message<String> consumeJob2Message(Message<String> message) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("consumeJob2Message -> Received message from gateway : " + message.getPayload());
        JobParameters jobParameters = new JobParametersBuilder()
             .addDate("rundate", new Date())
             .addString("messagePayload", message.getPayload())
             .toJobParameters();

        jobLauncher.run(mySecondJob, jobParameters);
        return null;
    }
}
