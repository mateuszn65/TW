package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DataSaver {
    private StringBuilder stringBuilder = new StringBuilder();
    public void add(String s){
        stringBuilder.append(s);
    }
    public void save(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(stringBuilder.toString());
        writer.close();
    }
}
