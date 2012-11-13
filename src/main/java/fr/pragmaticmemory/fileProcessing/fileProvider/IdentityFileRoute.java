package fr.pragmaticmemory.fileProcessing.fileProvider;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
public class IdentityFileRoute implements Route {

    private File file;


    public IdentityFileRoute(File file) {
        this.file = file;
    }


    public Reader getReader() throws Exception {
        return new FileReader(file);
    }


    public Writer getWriter() throws Exception {
        return new FileWriter(file);
    }
}
