package com.filefactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import com.models.CartItems;

public interface ResultGenerator {
    public void writeToFile(ArrayList < String > content);
    public void saveFile(Path path, ArrayList < CartItems > itemList) throws IOException;
}