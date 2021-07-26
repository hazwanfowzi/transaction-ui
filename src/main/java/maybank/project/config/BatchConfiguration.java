package maybank.project.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import maybank.project.dto.TransactionDTO;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    @Value("${file.input}")
    private String fileInput;

    @Bean
    public FlatFileItemReader<TransactionDTO> reader() {
        return new FlatFileItemReaderBuilder<TransactionDTO>().name("transactionItemReader")
            .resource(new ClassPathResource(fileInput))
            .delimited()
            .names(new String[] { "account_number", "trx_amount", "description", "trx_date", "trx_time", "customer_id" })
            .fieldSetMapper(new BeanWrapperFieldSetMapper<TransactionDTO>() {{
                setTargetType(TransactionDTO.class);
             }})
            .build();
    }

    @Bean
    public TransactionItemProcessor processor() {
        return new TransactionItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<TransactionDTO> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<TransactionDTO>().itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("INSERT INTO transaction (account_number, trx_amount, description, trx_date, trx_time, customer_id) VALUES (:account_number, :trx_amount, :description, :trx_date, :trx_time, :customer_id)")
            .dataSource(dataSource)
            .build();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1)
            .end()
            .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<TransactionDTO> writer) {
        return stepBuilderFactory.get("step1")
            .<TransactionDTO, TransactionDTO> chunk(10)
            .reader(reader())
            .processor(processor())
            .writer(writer)
            .build();
    }

}
