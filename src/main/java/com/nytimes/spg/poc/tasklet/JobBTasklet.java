package com.nytimes.spg.poc.tasklet;

import com.nytimes.spg.poc.service.BatchService;
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
public class JobBTasklet implements Tasklet {
    private final BatchService batchService;
    private final MessageChannel jobBStatusReportingChannel;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("JobBTasklet running!!!");
        batchService.doBatchJobBWork();
        jobBStatusReportingChannel.send(MessageBuilder.withPayload("Job B is done").build());
        return RepeatStatus.FINISHED;
    }
}
