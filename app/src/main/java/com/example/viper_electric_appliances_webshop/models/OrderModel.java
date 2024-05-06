package com.example.viper_electric_appliances_webshop.models;

import java.io.Serializable;

public class OrderModel implements Serializable {
    String productName, productPrice, sumQuantity;
    int sumPrice;

    public OrderModel(){};

    public OrderModel(String productName, String productPrice, String sumQuantity, int sumPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.sumQuantity = sumQuantity;
        this.sumPrice = sumPrice;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getSumQuantity() {
        return sumQuantity;
    }

    public void setSumQuantity(String sumQuantity) {
        this.sumQuantity = sumQuantity;
    }

    public int getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(int sumPrice) {
        this.sumPrice = sumPrice;
    }
}
