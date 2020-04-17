package com.example.duan_1.Model;

public class ItemProduct {
    private int imageItem ;
    private String productName ;

    public ItemProduct() {
    }

    public ItemProduct(int imageItem, String productName) {
        this.imageItem = imageItem;
        this.productName = productName;
    }

    public int getImageItem() {
        return imageItem;
    }

    public void setImageItem(int imageItem) {
        this.imageItem = imageItem;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
