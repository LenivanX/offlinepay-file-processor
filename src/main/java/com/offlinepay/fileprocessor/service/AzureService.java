package com.offlinepay.fileprocessor.service;

import org.springframework.stereotype.Service;

@Service
public interface AzureService {
    public byte[] readBlob(String filename);
}
