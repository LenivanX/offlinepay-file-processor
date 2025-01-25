package com.offlinepay.fileprocessor.service.impl;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.offlinepay.fileprocessor.entity.OfflinePayParent;
import com.offlinepay.fileprocessor.repo.OfflinePayParentRepo;
import com.offlinepay.fileprocessor.service.FileProcessorService;
import com.offlinepay.fileprocessor.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FileProcessorServiceImpl implements FileProcessorService {
    @Autowired
    OfflinePayParentRepo offlinePayParentRepo;
    @Autowired
    CommonUtils commonUtils;
    @Autowired
    Job job;
    @Autowired
    JobLauncher jobLauncher;

    @Override
    public void process() {
        try {
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString("AccountName=devstoreaccount1;AccountKey=Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw==;DefaultEndpointsProtocol=http;BlobEndpoint=http://127.0.0.1:10000/devstoreaccount1;QueueEndpoint=http://127.0.0.1:10001/devstoreaccount1;TableEndpoint=http://127.0.0.1:10002/devstoreaccount1;").buildClient();
            BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient("offlinepay");
            for (BlobItem blobItem : blobContainerClient.listBlobs()) {
                if (blobItem.getName().contains("input_file_")) {
                    log.info("processing input file: {}", blobItem.getName());
                    OfflinePayParent parentEntity = new OfflinePayParent();
                    parentEntity.setFilename(blobItem.getName());
                    parentEntity.setStage("file processing");
                    parentEntity.setStatus("new");
                    OfflinePayParent savedParentEntity = offlinePayParentRepo.saveAndFlush(parentEntity);
                    log.info("saved parent entity: {}", commonUtils.objToJson(savedParentEntity));
                    JobParameters jobParameters = new JobParametersBuilder().addLong("launch_time", System.currentTimeMillis()).addString("filename", blobItem.getName()).toJobParameters();
                    jobLauncher.run(job, jobParameters);
                }
            }
        } catch (Exception e) {
            log.error("exception caught: {}", e.getMessage());
        }
    }
}