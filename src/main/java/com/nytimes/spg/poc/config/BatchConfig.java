package com.nytimes.spg.poc.config;

import com.nytimes.spg.poc.tasklet.Job1Tasklet;
import com.nytimes.spg.poc.tasklet.Job2Tasklet;
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
    private Job1Tasklet job1Tasklet;
    @Autowired
    private Job2Tasklet job2Tasklet;

    @Bean
    public Job myFirstJob() {
        return jobBuilderFactory.get("myFirstJob")
                .incrementer(new RunIdIncrementer())
                .start(firstJobStep1())
                .build();
    }

    @Bean
    public Job mySecondJob() {
        return jobBuilderFactory.get("mySecondJob")
                .incrementer(new RunIdIncrementer())
                .start(secondJobStep1())
                .build();
    }

    private Step firstJobStep1() {
        return stepBuilderFactory.get("firstJobStep1")
                .tasklet(job1Tasklet)
                .build();
    }

    private Step secondJobStep1() {
        return stepBuilderFactory.get("secondJobStep1")
                .tasklet(job2Tasklet)
                .build();
    }
}
