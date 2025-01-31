package com.offlinepay.fileprocessor.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.offlinepay.fileprocessor.entity.OfflinePayError;
import com.offlinepay.fileprocessor.repo.OfflinePayErrorRepo;
import com.offlinepay.fileprocessor.repo.OfflinePayParentRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
@Slf4j
public class CommonUtils {
    @Autowired
    OfflinePayErrorRepo errorRepo;
    @Autowired
    OfflinePayParentRepo parentRepo;

    public String objToJson(Object o) {
        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            return o.toString();
        }
    }

    public void updateError(BigInteger code, String msg, String filename) {
        log.debug("update error method called with filename {}", filename);
        OfflinePayError error = new OfflinePayError();
        error.setCode(code);
        error.setMsg(msg);
        OfflinePayError savedError = errorRepo.saveAndFlush(error);
        log.info("saved error record:{}", savedError);
        parentRepo.updateError(savedError.getId(), filename);
        log.info("updated parent record");
    }
}
