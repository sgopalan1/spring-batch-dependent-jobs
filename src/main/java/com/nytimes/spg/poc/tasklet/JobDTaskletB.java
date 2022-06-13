package com.nytimes.spg.poc.tasklet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JobDTaskletB implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("JobDTaskletB running!!!");
        int b = 54;

        if (b == 4) {
            throw new Exception("4!!!!");
        }
        Thread.sleep(2000);

        log.info("JobDTaskletB DONE!!!");
        return RepeatStatus.FINISHED;
    }
}
