package com.filefactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.models.CartItems;
import com.opencsv.CSVWriter;


public class BillGenerator implements ResultGenerator {
    private ArrayList < String > content = new ArrayList < String > ();
    @Override
    public void writeToFile(ArrayList < String > orderDetails) {
        content = orderDetails;
    }
    @Override
    public void saveFile(Path filePath, ArrayList < CartItems > itemList) throws IOException {
    	Path p = Paths.get(""); 
        String directoryName = p.toAbsolutePath().toString();
        File fileInfo = new File(directoryName+"/src/main/java/Files/"+"Invoice.csv");
        FileWriter fw = new FileWriter(fileInfo);
        CSVWriter writer = new CSVWriter(fw, CSVWriter.DEFAULT_SEPARATOR , CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
        String[] data1 = {
            "Item",
            "Quantity",
            "Price",
            "Total Price"
        };
        writer.writeNext(data1);
        double totalprice = 0;
        for (int i = 0; i < itemList.size(); i++) {
            totalprice += itemList.get(i).getItemQuantity() * itemList.get(i).getPrice();
        }
        for (int i = 0; i < itemList.size(); i++) {
            if (i == 0) {
                String[] s = new String[4];
                s[0] = itemList.get(i).getInvoiceItemName();
                s[1] = String.valueOf(itemList.get(i).getItemQuantity());
                s[2] = String.valueOf(itemList.get(i).getPrice());
                s[3] = String.valueOf(totalprice);
                writer.writeNext(s);
            } else {
                String[] s = new String[3];
                s[0] = itemList.get(i).getInvoiceItemName();
                s[1] = String.valueOf(itemList.get(i).getItemQuantity());
                s[2] = String.valueOf(itemList.get(i).getPrice());
                writer.writeNext(s);
            }

        }
        System.out.println("Invoice is getting generated in -> " + fileInfo);
        writer.close();
    }
}