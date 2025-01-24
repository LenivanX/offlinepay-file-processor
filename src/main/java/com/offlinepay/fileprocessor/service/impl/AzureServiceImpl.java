package com.offlinepay.fileprocessor.service.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.offlinepay.fileprocessor.service.AzureService;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Component
public class AzureServiceImpl implements AzureService {
    @Override
    public byte[] readBlob(String filename) {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString("AccountName=devstoreaccount1;AccountKey=Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw==;DefaultEndpointsProtocol=http;BlobEndpoint=http://127.0.0.1:10000/devstoreaccount1;QueueEndpoint=http://127.0.0.1:10001/devstoreaccount1;TableEndpoint=http://127.0.0.1:10002/devstoreaccount1;").buildClient();
        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient("offlinepay");
        BlobClient blobClient = blobContainerClient.getBlobClient(filename);
        if (blobClient.exists()) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            blobClient.downloadStream(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
        return null;
    }
}
