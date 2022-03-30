package com.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.database.Database;
import com.filefactory.ResultHandler;
import com.models.CartItems;
import com.models.Invoice;
import com.models.Item;

public class Orders {
    private Database database = Database.getInstance();
    private ResultHandler file;
    private ArrayList < String > outputMessage = new ArrayList < String > ();
    private HashSet < String > cards = new HashSet < String > ();
    private ArrayList < CartItems > itemList = new ArrayList < CartItems > ();
    private Invoice currentInvoice = new Invoice();
    private double totalAmount = 0;
    
    public Orders(String filePath) {
        file = new ResultHandler(filePath);
    }

    public boolean initiateOrder() {
        try {
            file.readFile(true);
        } catch (Exception e) {
            outputMessage.add("File path seems to be wrong!! Please validate the file path & try again");
            return false;
        }
        getItems(file.getFileContent());
        return true;
    }

    public boolean validateOrder() {
        checkItemQuantity();
        checkItemCap();
        return outputMessage.isEmpty();
    }

    public void calculateFinalAmount() {
        itemList.forEach((item) -> {
            totalAmount += item.getItemQuantity() * item.getPrice();
        });
        currentInvoice.setTotalPrice(totalAmount);
    }

    public void checkoutOrder() {
        database.getOrders().add(currentInvoice);
        for (CartItems currentItem: itemList) {
            Item item = database.getItems().get(currentItem.getInvoiceItemName());
            item.setItemQuantity(item.getItemQuantity() - currentItem.getItemQuantity());

        }
        for (String card: cards) {
            if (!database.getCards().contains(card)) {
                database.getCards().add(card);
                System.out.println("Added card to the database!!");
            }
        }
        generateResultFile();
    }

    private void getItems(ArrayList < String > fileContent) {
        String firstLine = fileContent.get(0);
        String[] firstItem = firstLine.split(",");
        for (String contentLine: fileContent) {
            String[] item = contentLine.split(",");
            if (database.getItems().containsKey(item[0].toLowerCase())) {
                double priceItem = database.getItems().get(item[0].toLowerCase()).getItemPrice();
                try {
                    itemList.add(new CartItems(item[0].toLowerCase(), Integer.parseInt(item[1]), firstItem[2].replaceAll("\\s+", ""), priceItem));
                } catch (Exception e) {
                    System.out.println("Order cannot be processed!! Please check error logs!!");
                    outputMessage.add("Incorrect input file format");
                    break;
                }
            } else {
                outputMessage.add(item[0].toLowerCase() + " item is not available in inventory");
            }
        }
        if (!outputMessage.isEmpty()) {
            itemList.clear();
        }
    }
    
    private boolean checkItemQuantity() {
        StringBuilder message = new StringBuilder();
        for (CartItems currentItem: itemList) {
            Item item = database.getItems().get(currentItem.getInvoiceItemName());
            if (item.getItemQuantity() < currentItem.getItemQuantity()) {
                if (message.length() > 0)
                    message.append(",");
                message.append(currentItem.getInvoiceItemName() + " item is not available in sufficient quantity in inventory");
            } else {
                currentItem.setPrice(item.getItemPrice());
                if (!cards.contains(currentItem.getCardInfo()))
                    cards.add(currentItem.getCardInfo());
            }
        }
        if (message.length() > 0) {
            outputMessage.add("Please correct quantities.");
            outputMessage.add(message.toString());
        }
        return (message.length() == 0);
    }
    
    private boolean checkItemCap() {
        final int essentialsCap = 3;
        final int luxuryCap = 4;
        final int miscCap = 6;
        HashMap < String, Integer > map = new HashMap < String, Integer > ();
        Database database = Database.getInstance();
        for (CartItems orderItem: itemList) {
            map.put(database.getItems().get(orderItem.getInvoiceItemName()).getCategory(), map.getOrDefault(database.getItems().get(orderItem.getInvoiceItemName()).getCategory(), 0) + orderItem.getItemQuantity());
        }
        if (map.getOrDefault("Luxury", 0) > luxuryCap) {
            outputMessage.add("Luxury item cap is being violated");
            return false;
        } else if (map.getOrDefault("Essentials", 0) > essentialsCap) {
            outputMessage.add("Essential item cap is being violated");
            return false;
        } else if (map.getOrDefault("Misc", 0) > miscCap) {
            outputMessage.add("Misc item cap is being violated");
            return false;
        }
        return true;
    }

    public void generateResultFile() {
        if (outputMessage.isEmpty()) {
            try {
                file.writeResults(outputMessage, false, itemList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                file.writeResults(outputMessage, true, itemList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}