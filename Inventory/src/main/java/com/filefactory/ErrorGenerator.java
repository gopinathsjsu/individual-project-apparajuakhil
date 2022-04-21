package com.filefactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.models.CartItems;

public class ErrorGenerator implements ResultGenerator {
    private ArrayList < String > content;
    @Override
    public void writeToFile(ArrayList < String > errorMessage) {
        content = errorMessage;
    }
    @Override
    public void saveFile(Path filePath, ArrayList < CartItems > itemList) throws IOException {
    	Path p = Paths.get(""); 
        String directoryName = p.toAbsolutePath().toString();
        File fileInfo = new File(directoryName+"/src/main/java/Files/"+"Error.txt");
    	System.out.println("Error logs are generated in -> " + fileInfo);
        FileWriter errorFile = new FileWriter(fileInfo);
        for (String contentLine: content)
            errorFile.write(contentLine + "\n");
        errorFile.close();
    }
}