package fr.pragmaticmemory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static List<String> readLines(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> lines = new ArrayList<String>();
        String line = reader.readLine();
        while (line != null) {
            lines.add(line);
            line = reader.readLine();
        }
        reader.close();
        return lines;
    }


    public static void writeLines(List<String> lines, String file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (int i = 0; i < lines.size() - 1; i++) {
            writer.write(lines.get(i));
            writer.newLine();
        }
        writer.write(lines.get(lines.size() - 1));
        writer.flush();
        writer.close();
    }
}
