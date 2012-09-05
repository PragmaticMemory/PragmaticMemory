package fr.pragmaticmemory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static List<String> readLines(String filePath) throws IOException {
        return readLines(new FileReader(filePath));
    }


    public static List<String> readLines(Reader reader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<String> lines = new ArrayList<String>();
        String line = bufferedReader.readLine();
        while (line != null) {
            lines.add(line);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        return lines;
    }


    public static void writeLines(List<String> lines, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        for (int i = 0; i < lines.size() - 1; i++) {
            writer.write(lines.get(i));
            writer.newLine();
        }
        writer.write(lines.get(lines.size() - 1));
        writer.flush();
        writer.close();
    }
}
