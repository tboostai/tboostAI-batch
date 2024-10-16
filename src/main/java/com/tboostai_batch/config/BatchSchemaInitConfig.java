package com.tboostai_batch.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class BatchSchemaInitConfig {

    @Bean
    public DataSourceInitializer dataSourceInitializer(@Qualifier("batchDataSource") DataSource batchDataSource) throws SQLException {
        // Check BATCH_JOB_INSTANCE existed
        if (doesTableExist(batchDataSource)) {
            // Skip initialization
            return null;
        }

        // Init batch metadata tables
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("org/springframework/batch/core/schema-mysql.sql"));

        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(batchDataSource);
        initializer.setDatabasePopulator(resourceDatabasePopulator);
        return initializer;
    }

    // Check if batch metadata tables are existed
    private boolean doesTableExist(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            try (ResultSet resultSet = metaData.getTables(null, null, "BATCH_JOB_INSTANCE", new String[]{"TABLE"})) {
                return resultSet.next();
            }
        }
    }
}
