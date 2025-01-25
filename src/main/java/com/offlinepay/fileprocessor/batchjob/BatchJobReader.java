package com.offlinepay.fileprocessor.batchjob;

import com.offlinepay.fileprocessor.service.AzureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;

@StepScope
@Slf4j
public class BatchJobReader extends FlatFileItemReader<FieldSet> implements StepExecutionListener {
    @Autowired
    AzureService azureService;

    public BatchJobReader() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames("ban", "accno", "amount", "date");
        DefaultLineMapper<FieldSet> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSet -> fieldSet);
        setLineMapper(lineMapper);
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        byte[] byteArray = null;
        String filename = BatchJobListener.parentEntity.getFilename();
        log.info("downloading file {} from container", filename);
        try {
            byteArray = azureService.readBlob(filename);
        } catch (Exception e) {
            log.error("failed downloading file {} from container: {}", filename, e.getMessage());
        }
        if (byteArray != null) {
            setResource(new ByteArrayResource(byteArray));
        } else {
            throw new RuntimeException("file " + filename + " not found in container");
        }
    }

}
