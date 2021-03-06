package com.nytimes.spg.poc.tasklet;

import com.nytimes.spg.poc.service.BatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JobCTasklet implements Tasklet {
    private final BatchService batchService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("JobCTasklet running!!!");
        batchService.doBatchJobCWork();
        log.info("JobCTasklet done!!!");
        return RepeatStatus.FINISHED;
    }
}
