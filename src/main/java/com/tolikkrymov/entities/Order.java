package com.tolikkrymov.entities;

import java.util.List;

public class Order {

    private long id;
    private String address;
    private String fio;
    private String phone;
    private String email;
    private long idPaymentType;
    private long idDeliveryType;
    private long price;
    private boolean applied;

    private Payment payment;
    private Delivery delivery;
    private List<ProductOrder> productsOrders;

    public Order() {

    }

    public Order(long id,
                 String address,
                 String fio,
                 String phone,
                 String email,
                 long idPaymentType,
                 long idDeliveryType,
                 long price,
                 boolean applied) {

        this.id = id;
        this.address = address;
        this.fio = fio;
        this.phone = phone;
        this.email = email;
        this.idPaymentType = idPaymentType;
        this.idDeliveryType = idDeliveryType;
        this.price = price;
        this.applied = applied;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getIdPaymentType() {
        return idPaymentType;
    }

    public void setIdPaymentType(long idPaymentType) {
        this.idPaymentType = idPaymentType;
    }

    public long getIdDeliveryType() {
        return idDeliveryType;
    }

    public void setIdDeliveryType(long idDeliveryType) {
        this.idDeliveryType = idDeliveryType;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public boolean getApplied() {
        return applied;
    }

    public void setApplied(boolean applied) {
        this.applied = applied;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public List<ProductOrder> getProductsOrders() {
        return productsOrders;
    }

    public void setProductsOrders(List<ProductOrder> productsOrders) {
        this.productsOrders = productsOrders;
    }
}
