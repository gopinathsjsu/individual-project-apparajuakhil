package com.models;

public class CartItems {
    private String invoiceItemName;
    private int itemQuantity;
    private String cardInfo;
    private double price;

    public CartItems(String invoiceItemName, int itemQuantity, String cardInfo, double price) {
        this.invoiceItemName = invoiceItemName;
        this.itemQuantity = itemQuantity;
        this.cardInfo = cardInfo;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getInvoiceItemName() {
        return invoiceItemName;
    }

    public void setInvoiceItemName(String invoiceItemName) {
        this.invoiceItemName = invoiceItemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(String cardInfo) {
        this.cardInfo = cardInfo;
    }

}