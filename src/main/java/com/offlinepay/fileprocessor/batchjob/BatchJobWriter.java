package com.offlinepay.fileprocessor.batchjob;

import com.offlinepay.fileprocessor.entity.OfflinePayChild;
import com.offlinepay.fileprocessor.repo.OfflinePayChildRepo;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

@StepScope
public class BatchJobWriter implements ItemWriter<OfflinePayChild> {
    @Autowired
    OfflinePayChildRepo offlinePayChildRepo;

    @Override
    public void write(Chunk<? extends OfflinePayChild> chunk) throws Exception {
        offlinePayChildRepo.saveAll(chunk);
    }
}
