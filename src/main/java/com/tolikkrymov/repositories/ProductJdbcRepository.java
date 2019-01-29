package com.tolikkrymov.repositories;

import com.tolikkrymov.Resources;
import com.tolikkrymov.entities.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductJdbcRepository {

    private JdbcTemplate jdbcTemplate;

    public ProductJdbcRepository() {
        jdbcTemplate = Resources.getJdbcTemplate();
    }

    private class ProductRowMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new Product(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getLong("price"),
                    rs.getString("information"),
                    rs.getLong("id_type"),
                    rs.getLong("count"));
        }
    }

    public Product findById(long id) {

        return jdbcTemplate.queryForObject(
                "SELECT * FROM products WHERE id = ?",
                new ProductRowMapper(),
                id);
    }

    public List<Product> findAllProducts() {

        return jdbcTemplate.query(
                "SELECT * FROM products",
                new ProductRowMapper());
    }

    public List<Product> findByPage(int page, int productPerPage) {

        int offset = (page - 1) * productPerPage;

        return jdbcTemplate.query(
                "SELECT * FROM products LIMIT ? OFFSET ?",
                new ProductRowMapper(),
                productPerPage,
                offset);
    }

    public List<Product> findByPageAndIdType(int page, int productPerPage, long id_type) {

        int offset = (page - 1) * productPerPage;

        return jdbcTemplate.query(
                "SELECT * FROM products WHERE id_type = ? LIMIT ? OFFSET ?",
                new ProductRowMapper(),
                id_type,
                productPerPage,
                offset);
    }

    public int getPageCount(int productPerPage) {

        Integer integer = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) AS product_count FROM products",
                Integer.class);

        int totalProducts = (integer != null) ? integer : 0;

        return (totalProducts + productPerPage - 1) / productPerPage;
    }

    public int getPageCountByIdType(int productPerPage, long id_type) {

        Integer integer = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) AS product_count FROM products WHERE id_type = ?",
                Integer.class,
                id_type);

        int totalProducts = (integer != null) ? integer : 0;

        return (totalProducts + productPerPage - 1) / productPerPage;
    }

    public int getNewId() {

        Integer lastId;
        try {
            lastId = jdbcTemplate.queryForObject(
                    "SELECT id FROM products ORDER BY id DESC LIMIT 1",
                    Integer.class);
        }
        catch (EmptyResultDataAccessException exception) {
            lastId = 0;
        }

        return lastId + 1;
    }

    public int deleteById(long id) {

        return jdbcTemplate.update(
                "DELETE FROM products WHERE id = ?",
                id);
    }

    public int insert(Product product) {

        return jdbcTemplate.update(
                "INSERT INTO products (id, name, price, information, id_type, count)" +
                        " VALUES (?, ?, ?, ?, ?, ?)",
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getInformation(),
                product.getIdType(),
                product.getCount());
    }

    public int update(Product product) {

        return jdbcTemplate.update(
                "UPDATE products SET name = ?, price = ?, information = ?, id_type = ?, count = ? WHERE id = ?",
                product.getName(),
                product.getPrice(),
                product.getInformation(),
                product.getIdType(),
                product.getCount(),
                product.getId());
    }
}
