package com.offlinepay.fileprocessor.batchjob;

import com.offlinepay.fileprocessor.entity.OfflinePayChild;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.transform.FieldSet;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

@StepScope
@Slf4j
public class BatchJobProcessor implements ItemProcessor<FieldSet, OfflinePayChild> {
    @Override
    public OfflinePayChild process(FieldSet item) throws Exception {
        log.info("processor item: {}", item);
        String ban = item.readString("ban");
        String accNo = item.readString("accno");
        BigDecimal amount = item.readBigDecimal("amount");
        String date = item.readString("date");
        Date transactionDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        OfflinePayChild offlinePayChild = new OfflinePayChild();
        offlinePayChild.setParentId(BatchJobListener.parentEntity.getId());
        offlinePayChild.setBan(ban);
        offlinePayChild.setAccountNo(accNo);
        offlinePayChild.setAmount(amount);
        offlinePayChild.setTransactionDate(transactionDate);
        BatchJobListener.incrementTotalRecords(BigInteger.ONE);
        BatchJobListener.incrementTotalAmount(amount);
        return offlinePayChild;
    }
}
