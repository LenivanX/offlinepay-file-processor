package com.offlinepay.fileprocessor.batchjob;

import com.offlinepay.fileprocessor.service.AzureService;
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
        byte[] byteArray = azureService.readBlob(BatchJobListener.parentEntity.getFilename());
        if (byteArray != null) {
            setResource(new ByteArrayResource(byteArray));
        }
    }

}
