package com.nytimes.spg.poc.spring.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {
    private final MessageChannel job1Channel;

    @Scheduled(initialDelay = 3000, fixedDelay = 9999999)
    public void springIntegrationJob1() throws InterruptedException {
        log.info("Starting springIntegrationJob1");
        Thread.sleep(3 * 1000); // sleep for 3 sec
        Message<String> message = MessageBuilder.withPayload("springIntegrationJob1 is done").build();
        job1Channel.send(message);
        log.info("Ending springIntegrationJob1");
    }
}
