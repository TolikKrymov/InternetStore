package com.tolikkrymov.entities;

public class ProductOrder {

    private Long idOrder;
    private Long idProduct;
    private Long count;

    private Product product;

    public ProductOrder() {}

    public ProductOrder(Long idOrder, Long idProduct, Long count) {
        this.idOrder = idOrder;
        this.idProduct = idProduct;
        this.count = count;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
