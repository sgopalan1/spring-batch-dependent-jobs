package com.nytimes.spg.poc.config;

import com.nytimes.spg.poc.tasklet.JobBTasklet;
import com.nytimes.spg.poc.tasklet.JobCTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobBTasklet jobBTasklet;
    @Autowired
    private JobCTasklet jobCTasklet;

    /* Job B Config */
    @Bean
    public Job jobB() {
        return jobBuilderFactory.get("jobB")
                .incrementer(new RunIdIncrementer())
                .start(jobBStep1())
                .build();
    }

    private Step jobBStep1() {
        return stepBuilderFactory.get("jobBStep1")
                .tasklet(jobBTasklet)
                .build();
    }

    /* Job C Config */
    @Bean
    public Job jobC() {
        return jobBuilderFactory.get("jobC")
                .incrementer(new RunIdIncrementer())
                .start(jobCStep1())
                .build();
    }

    private Step jobCStep1() {
        return stepBuilderFactory.get("jobCStep1")
                .tasklet(jobCTasklet)
                .build();
    }
}
