package com.nytimes.spg.poc.spring.batch;

import lombok.RequiredArgsConstructor;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class Scheduler {
    @Autowired
    private MessageChannel jobAStatusReportingChannel;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier("jobD")
    private Job jobD;
    @Autowired
    @Qualifier("jobE")
    private Job jobE;

    @Scheduled(initialDelay = 9999999, fixedDelay = 9999999)
    public void jobA() throws InterruptedException {
        log.info("Starting jobA");
        Thread.sleep(1 * 1000); // do "work": sleep for 3 sec
        Message<String> message = MessageBuilder.withPayload("Job A is done").build();
        jobAStatusReportingChannel.send(message); // transmit status
        jmsTemplate.convertAndSend("spg.poc", "Job A is done - JMS");
        log.info("Ending jobA");
    }

    @Scheduled(initialDelay = 9999999, fixedDelay = 9999999)
    public void jobD() throws InterruptedException, JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("Starting jobD");
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("uniqIdentifier", "9")
                .toJobParameters();

        jobLauncher.run(jobD, jobParameters);
        log.info("Ending jobD");
    }

    @Scheduled(initialDelay = 9999999, fixedDelay = 9999999)
    public void jobE() throws InterruptedException, JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("Starting jobE");
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("uniqIdentifier", "3")
                .toJobParameters();

        jobLauncher.run(jobE, jobParameters);
        log.info("Ending jobE");
    }
}
