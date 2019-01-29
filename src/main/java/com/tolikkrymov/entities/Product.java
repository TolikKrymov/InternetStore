package com.tolikkrymov.entities;

public class Product {

    private long id;
    private String name;
    private long price;
    private String information;
    private long idType;
    private long count;

    public Product() { }

    public Product(long id, String name, long price, String information, long id_type, long count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.information = information;
        this.idType = id_type;
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() { return price; }

    public void setPrice(long price) { this.price = price; }

    public String getInformation() { return information; }

    public void setInformation(String information) { this.information = information; }

    public long getIdType() { return idType; }

    public void setIdType(long idType) { this.idType = idType; }

    public long getCount() { return count; }

    public void setCount (long count) { this.count = count; }
}
