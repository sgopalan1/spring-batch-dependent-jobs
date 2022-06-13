package com.nytimes.spg.poc.config;

import com.nytimes.spg.poc.spring.batch.deciders.JobEDecider;
import com.nytimes.spg.poc.tasklet.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Autowired
    private JobDTaskletA jobDTaskletA;
    @Autowired
    private JobDTaskletB jobDTaskletB;
    @Autowired
    private JobETaskletA jobETaskletA;
    @Autowired
    private JobETaskletB jobETaskletB;
    @Autowired
    private JobEDecider jobEDecider;
    @Autowired
    private JobFTasklet jobFTasklet;

    /* Job B Config */
    @Qualifier("jobB")
    @Bean
    public Job jobB() {
        return jobBuilderFactory.get("jobBZ")
                .incrementer(new RunIdIncrementer())
                .start(jobBStep1())
                .build();
    }

    private Step jobBStep1() {
        return stepBuilderFactory.get("jobBZStep1")
                .tasklet(jobBTasklet)
                .build();
    }

    /* Job C Config */
    @Bean
    @Qualifier("jobC")
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

    /* Job D Config */
    @Bean
    @Qualifier("jobD")
    public Job jobD() {
        return jobBuilderFactory.get("jobD")
                .start(jobDStepA())
                .on("COMPLETED").to(jobDStepB())
                .end()
                .build();
    }

    private Step jobDStepA() {
        return stepBuilderFactory.get("jobDStepA")
                .tasklet(jobDTaskletA)
                .build();
    }

    private Step jobDStepB() {
        return stepBuilderFactory.get("jobDStepB")
                .tasklet(jobDTaskletB)
                .build();
    }

    /* Job E Config */
    @Bean
    @Qualifier("jobE")
    public Job jobE() {
        return jobBuilderFactory.get("jobE")
                .start(jobEStepA())
                .next(jobEDecider).on("COMPLETED").to(jobEStepB())
                .end()
                .build();
    }

    private Step jobEStepA() {
        return stepBuilderFactory.get("jobEStepA")
                .tasklet(jobETaskletA)
                .build();
    }

    private Step jobEStepB() {
        return stepBuilderFactory.get("jobEStepB")
                .tasklet(jobETaskletB)
                .build();
    }

    /* Job F Config */
    @Bean
    @Qualifier("jobF")
    public Job jobF() {
        return jobBuilderFactory.get("jobF")
                .start(jobFStep())
                .build();
    }

    private Step jobFStep() {
        return stepBuilderFactory.get("jobFStep")
                .tasklet(jobFTasklet)
                .build();
    }
}
