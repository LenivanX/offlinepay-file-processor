package com.offlinepay.fileprocessor.batchjob;

import com.offlinepay.fileprocessor.entity.OfflinePayChild;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchJobConfig {
    @Bean
    BatchJobReader reader() {
        return new BatchJobReader();
    }

    @Bean
    BatchJobProcessor processor() {
        return new BatchJobProcessor();
    }

    @Bean
    BatchJobWriter writer() {
        return new BatchJobWriter();
    }

    @Bean
    Step batchJobStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("op-fileprocess-step", jobRepository).<FieldSet, OfflinePayChild>chunk(5, platformTransactionManager).reader(reader()).processor(processor()).writer(writer()).build();
    }

    @Bean
    Job batchJob(JobRepository jobRepository, Step step, BatchJobListener listener) {
        return new JobBuilder("op-fileprocess-job", jobRepository).listener(listener).flow(step).end().build();
    }
}
