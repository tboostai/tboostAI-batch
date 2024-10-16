package com.tboostai_batch.config;

import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.lang.NonNull;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig extends DefaultBatchConfiguration {

    private final DataSource batchDataSource;

    public BatchConfig(@Qualifier("batchDataSource") DataSource batchDataSource) {
        this.batchDataSource = batchDataSource;
    }

    @Override
    @NonNull
    public DataSource getDataSource() {
        return this.batchDataSource;
    }

    @Override
    @NonNull
    public PlatformTransactionManager getTransactionManager() {
        return new DataSourceTransactionManager(batchDataSource);
    }
}
