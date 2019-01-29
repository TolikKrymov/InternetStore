package com.tolikkrymov.entities;

import java.util.List;

public class Order {

    private Long id;
    private String address;
    private String fio;
    private String phone;
    private String email;
    private Long idPaymentType;
    private Long idDeliveryType;
    private Long price;
    private Boolean applied;

    private Payment payment;
    private Delivery delivery;
    private List<ProductOrder> productsOrders;

    public Order() {

    }

    public Order(Long id,
                 String address,
                 String fio,
                 String phone,
                 String email,
                 Long idPaymentType,
                 Long idDeliveryType,
                 Long price,
                 Boolean applied) {

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getIdPaymentType() {
        return idPaymentType;
    }

    public void setIdPaymentType(Long idPaymentType) {
        this.idPaymentType = idPaymentType;
    }

    public Long getIdDeliveryType() {
        return idDeliveryType;
    }

    public void setIdDeliveryType(Long idDeliveryType) {
        this.idDeliveryType = idDeliveryType;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Boolean getApplied() {
        return applied;
    }

    public void setApplied(Boolean applied) {
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
