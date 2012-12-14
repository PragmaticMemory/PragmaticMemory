package fr.pragmaticmemory.fileProcessing.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static List<String> readLines(String filePath) throws IOException {
        return readLines(new File(filePath));
    }


    public static List<String> readLines(File file) throws IOException {
        return readLines(new FileReader(file));
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
        writeLines(lines, new File(filePath));
    }


    public static void writeLines(List<String> lines, File file) throws IOException {
        writeLines(lines, new FileWriter(file));
    }


    public static void writeLines(List<String> lines, Writer writer) throws IOException {
        if (lines.isEmpty()) {
            return;
        }
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        for (int i = 0; i < lines.size() - 1; i++) {
            bufferedWriter.write(lines.get(i));
            bufferedWriter.newLine();
        }
        bufferedWriter.write(lines.get(lines.size() - 1));
        bufferedWriter.flush();
        bufferedWriter.close();
    }


    public static void createDirectory(File file) {
        if (file.isFile()) {
            throw new IllegalArgumentException("The File instance argument must represent a directory and not a file.");
        }
        final File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            createDirectory(parentFile);
        }
        file.mkdir();
    }


    public static void createFile(File file) throws IOException {
        if (file.isDirectory()) {
            throw new IllegalArgumentException("The File instance argument must represent a file and not a directory.");
        }
        final File parentDirectory = file.getParentFile();
        if (!parentDirectory.exists()) {
            createDirectory(parentDirectory);
        }
        file.createNewFile();
    }
}
