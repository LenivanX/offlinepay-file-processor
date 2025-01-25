package com.offlinepay.fileprocessor.service;

import org.springframework.stereotype.Service;

@Service
public interface AzureService {
    byte[] readBlob(String filename) throws Exception;
}
