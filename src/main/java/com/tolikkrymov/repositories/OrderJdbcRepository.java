package com.tolikkrymov.repositories;

import com.tolikkrymov.Resources;
import com.tolikkrymov.entities.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrderJdbcRepository {


    private JdbcTemplate jdbcTemplate;

    public OrderJdbcRepository() {
        jdbcTemplate = Resources.getJdbcTemplate();
    }

    private class OrderRowMapper implements RowMapper<Order> {

        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new Order(
                    rs.getLong("id"),
                    rs.getString("address"),
                    rs.getString("fio"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getLong("id_payment_type"),
                    rs.getLong("id_delivery_type"),
                    rs.getLong("price"),
                    rs.getBoolean("applied"));
        }
    }

    private class DeliveryRowMapper implements RowMapper<Delivery> {

        @Override
        public Delivery mapRow(ResultSet rs, int rowNum) throws  SQLException {

            return new Delivery(
                    rs.getLong("id"),
                    rs.getString("name"));
        }
    }

    private class PaymentRowMapper implements RowMapper<Payment> {

        @Override
        public Payment mapRow(ResultSet rs, int rowNum) throws  SQLException {

            return new Payment(
                    rs.getLong("id"),
                    rs.getString("name"));
        }
    }

    private class ProductOrderRowMapper implements RowMapper<ProductOrder> {

        @Override
        public ProductOrder mapRow(ResultSet rs, int rowNum) throws  SQLException {

            return new ProductOrder(
                    rs.getLong("id_order"),
                    rs.getLong("id_product"),
                    rs.getLong("count"));
        }
    }

    public Order findById(long id) {

        return jdbcTemplate.queryForObject(
                "SELECT * FROM orders WHERE id = ?",
                new OrderRowMapper(),
                id);
    }

    public List<Order> findOrdersByPage(int page, int ordersPerPage) {

        int offset = (page - 1) * ordersPerPage;

        return jdbcTemplate.query(
                "SELECT * FROM orders ORDER BY id DESC  LIMIT ? OFFSET ? ",
                new OrderRowMapper(),
                ordersPerPage,
                offset);
    }

    public int getPageCount(int ordersPerPage) {

        Integer integer = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) AS order_count FROM orders",
                Integer.class);

        int totalOrders = (integer != null) ? integer : 0;

        return (totalOrders + ordersPerPage - 1) / ordersPerPage;
    }

    public Long getNewId() {

        Long lastId;
        try {
            lastId = jdbcTemplate.queryForObject(
                    "SELECT id FROM orders ORDER BY id DESC LIMIT 1",
                    Long.class);
        }
        catch (EmptyResultDataAccessException exception) {
            lastId = 0l;
        }

        return lastId + 1;
    }

    public int insert(Order order) {

        return jdbcTemplate.update(
                "INSERT INTO orders (id, address, fio, phone, email, id_payment_type, id_delivery_type, price, applied)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                order.getId(),
                order.getAddress(),
                order.getFio(),
                order.getPhone(),
                order.getEmail(),
                order.getIdPaymentType(),
                order.getIdDeliveryType(),
                order.getPrice(),
                order.getApplied());
    }

    public Delivery findDeliveryById(long id) {

        return jdbcTemplate.queryForObject(
                "SELECT * FROM deliveries WHERE id = ?",
                new DeliveryRowMapper(),
                id);
    }

    public List<Delivery> findAllDeliveries() {

        return jdbcTemplate.query(
                "SELECT * FROM deliveries",
                new DeliveryRowMapper());
    }

    public Payment findPaymentById(long id) {

        return jdbcTemplate.queryForObject(
                "SELECT * FROM payments WHERE id = ?",
                new PaymentRowMapper(),
                id);
    }

    public List<Payment> findAllPayments() {

        return jdbcTemplate.query(
                "SELECT * FROM payments",
                new PaymentRowMapper());
    }

    public List<ProductOrder> findAllProductsByOrderId(long idOrder) {

        return jdbcTemplate.query(
                "SELECT * FROM products_orders WHERE id_order = ?",
                new ProductOrderRowMapper(),
                idOrder);
    }

    public int insert(ProductOrder productOrder) {

        return jdbcTemplate.update(
                "INSERT INTO products_orders (id_order, id_product, count)" +
                        " VALUES (?, ?, ?)",
                productOrder.getIdOrder(),
                productOrder.getIdProduct(),
                productOrder.getCount());
    }
}
