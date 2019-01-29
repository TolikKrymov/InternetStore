package com.tolikkrymov;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tolikkrymov.repositories.OrderJdbcRepository;
import com.tolikkrymov.repositories.ProductJdbcRepository;
import com.tolikkrymov.repositories.ProductTypeJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public final class Resources {

    @Autowired
    private static JdbcTemplate jdbcTemplate;

    public static final ProductJdbcRepository productJdbcRepository = new ProductJdbcRepository();

    public static final ProductTypeJdbcRepository productTypeJdbcRepository = new ProductTypeJdbcRepository();

    public static final OrderJdbcRepository orderJdbcRepository = new OrderJdbcRepository();

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static JdbcTemplate getJdbcTemplate() {

        if (jdbcTemplate == null){
            DriverManagerDataSource dataSources = new DriverManagerDataSource();
            dataSources.setDriverClassName("org.h2.Driver");
            dataSources.setUrl("jdbc:h2:file:~/test");
            dataSources.setUsername("sa");
            dataSources.setPassword("");
            jdbcTemplate = new JdbcTemplate(dataSources);
        }

        return jdbcTemplate;
    }
}
