package com.tolikkrymov.entities;

public class ProductOrder {

    private long idOrder;
    private long idProduct;
    private long count;

    private Product product;

    public ProductOrder() {}

    public ProductOrder(long idOrder, long idProduct, long count) {
        this.idOrder = idOrder;
        this.idProduct = idProduct;
        this.count = count;
    }

    public long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(long idOrder) {
        this.idOrder = idOrder;
    }

    public long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
