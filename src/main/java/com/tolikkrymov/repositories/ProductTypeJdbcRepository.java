package com.tolikkrymov.repositories;

import com.tolikkrymov.Resources;
import com.tolikkrymov.entities.ProductType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductTypeJdbcRepository {

    private JdbcTemplate jdbcTemplate;

    public ProductTypeJdbcRepository() {

        jdbcTemplate = Resources.getJdbcTemplate();
    }

    private class ProductTypeMapper implements RowMapper<ProductType> {

        @Override
        public ProductType mapRow(ResultSet resultSet, int i) throws SQLException {

            return new ProductType(
                    resultSet.getLong("id"),
                    resultSet.getString("name"));
        }
    }

    public ProductType findById(long id) {

        return jdbcTemplate.queryForObject(
                "SELECT * FROM product_types WHERE id = ?",
                new Object[] { id },
                new ProductTypeMapper());
    }

    public List<ProductType> findAll() {

        return jdbcTemplate.query(
                "SELECT * FROM product_types",
                new ProductTypeMapper());
    }
}
