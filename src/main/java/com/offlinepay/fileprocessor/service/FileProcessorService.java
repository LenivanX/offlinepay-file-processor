package com.offlinepay.fileprocessor.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public interface FileProcessorService {
    @Async
    public void process();
}
