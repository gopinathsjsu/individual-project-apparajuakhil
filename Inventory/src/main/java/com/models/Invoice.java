package com.models;

import java.util.HashSet;

public class Invoice {
    private double totalPrice;
    private HashSet < Item > itemList = new HashSet < Item > ();
    public Invoice() {};

    public HashSet < Item > getItemList() {
        return itemList;
    }

    public void setItemList(HashSet < Item > itemList) {
        this.itemList = itemList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}