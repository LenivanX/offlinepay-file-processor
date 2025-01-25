package com.offlinepay.fileprocessor.batchjob;

import com.offlinepay.fileprocessor.entity.OfflinePayParent;
import com.offlinepay.fileprocessor.repo.OfflinePayParentRepo;
import com.offlinepay.fileprocessor.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

@Component
@Slf4j
public class BatchJobListener implements JobExecutionListener {
    public static OfflinePayParent parentEntity;
    public static BigInteger totalRecords = BigInteger.ZERO;
    public static BigDecimal totalAmount = BigDecimal.ZERO;
    @Autowired
    OfflinePayParentRepo offlinePayParentRepo;

    public static void incrementTotalRecords(BigInteger inc) {
        totalRecords = totalRecords.add(inc);
    }

    public static void incrementTotalAmount(BigDecimal inc) {
        totalAmount = totalAmount.add(inc);
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("batch job started with instance id: {}", jobExecution.getJobInstance().getInstanceId());
        String filename = jobExecution.getJobParameters().getString("filename");
        Optional<OfflinePayParent> optionalOfflinePayParent = offlinePayParentRepo.findByFilename(filename);
        if (optionalOfflinePayParent.isPresent()) {
            parentEntity = optionalOfflinePayParent.get();
        } else {
            throw new RuntimeException("parent entity not found with filename: {}" + filename);
        }
        log.info("fetched parent entity for batch job: {}", CommonUtils.objToJson(parentEntity));
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        offlinePayParentRepo.updateRecord(totalRecords, totalAmount, "completed", parentEntity.getId());
        totalRecords = BigInteger.ZERO;
        totalAmount = BigDecimal.ZERO;
        parentEntity = null;
        log.info("batch job ended with instance id: {} ended with status: {}", jobExecution.getJobInstance().getInstanceId(), jobExecution.getStatus());
    }
}
