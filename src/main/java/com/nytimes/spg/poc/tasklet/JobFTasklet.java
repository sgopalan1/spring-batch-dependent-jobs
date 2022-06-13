package com.nytimes.spg.poc.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobFTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("JobFTasklet running!!!");
        Object filename = chunkContext.getStepContext().getJobParameters().get("input.file.name");
        Thread.sleep(2000);
        log.info("JobFTasklet with file name: {} done!!!", filename);
        return RepeatStatus.FINISHED;
    }
}
