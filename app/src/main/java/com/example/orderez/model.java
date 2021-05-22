package com.example.orderez;

public class model {
    String name;
    String order;
    String extra;

    model() {

    }

    public model(String name, String order, String extra) {
        this.name = name;
        this.order = order;
        this.extra = extra;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
