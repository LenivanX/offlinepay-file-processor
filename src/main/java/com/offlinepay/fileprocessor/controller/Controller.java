package com.offlinepay.fileprocessor.controller;

import com.offlinepay.fileprocessor.service.FileProcessorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAsync
@Slf4j
public class Controller {
    @Autowired
    FileProcessorService service;

    @GetMapping("invoke")
    public String invoke() {
        service.process();
        return "started";
    }
}
