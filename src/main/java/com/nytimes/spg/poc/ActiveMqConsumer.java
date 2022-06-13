package com.nytimes.spg.poc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.Date;

@Component
@Slf4j
public class ActiveMqConsumer implements MessageListener {
    @Autowired
    private Job jobC;
    @Autowired
    private JobLauncher jobLauncher;

    @Override
    @JmsListener(destination = "${active-mq.topic:spg.poc}")
    public void onMessage(Message message) {
        try{
            message.acknowledge();
            TextMessage textMessage = (TextMessage) message;
            String payLoad = textMessage.getText();
            log.info("Received Message: {}", payLoad);

            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("rundate", new Date())
                    .addString("messagePayload", payLoad)
                    .toJobParameters();

            jobLauncher.run(jobC, jobParameters);
        } catch(Exception e) {
            log.error("Received Exception parsing ActiveMQ message", e);
        }

    }
}
