package com.nytimes.spg.poc.spring.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {
    private final MessageChannel jobAStatusReportingChannel;

    @Scheduled(initialDelay = 2000, fixedDelay = 9999999)
    public void jobA() throws InterruptedException {
        log.info("Starting jobA");
        Thread.sleep(3 * 1000); // do "work": sleep for 3 sec
        Message<String> message = MessageBuilder.withPayload("Job A is done").build();
        jobAStatusReportingChannel.send(message); // transmit status
        log.info("Ending jobA");
    }
}
