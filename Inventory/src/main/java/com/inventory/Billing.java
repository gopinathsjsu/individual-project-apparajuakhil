package com.inventory;

import java.io.IOException;
import java.util.Scanner;

import com.controller.Orders;
import com.database.Database;

public class Billing {
    public static void main(String args[]) throws IOException {
    	loadDB();
        System.out.println("Hi There!");
    	Billing billObject = new Billing();
        billObject.initOrderInput();
    }
    
    private static void loadDB() { 
    	Database db = Database.getInstance();
        db.initDB();
    }
    
    private void initOrderInput() { 
        Scanner sc = new Scanner(System.in).useDelimiter("\n");
        System.out.println("Please enter the order file path");
        String inputFilePath = "";
        try {
            inputFilePath = sc.next();
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initTransaction(inputFilePath);
    }
    
    private void initTransaction(String filePath) {
        Orders Orders = new Orders(filePath);
        if (Orders.initiateOrder()) {
            System.out.println("Processing Order.. ");
            System.out.println("PLEASE WAIT");
            if (Orders.validateOrder()) {
                Orders.calculateFinalAmount();
                System.out.println("Order Accepted!!");
                Orders.checkoutOrder();
                System.out.println("Order has been placed successfully!!");
            } else {
                System.out.println("FAILED!! Order cannot be processed!! Please check error logs!!");
                Orders.generateResultFile();
            }
        } else {
            System.out.println("File path seems to be wrong!! Please validate the file path & try again");
            Orders.generateResultFile();
        }
    }
}