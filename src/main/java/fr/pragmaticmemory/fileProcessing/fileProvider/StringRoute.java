package fr.pragmaticmemory.fileProcessing.fileProvider;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
public class StringRoute implements Route {

    private String line;
    private StringWriter stringWriter;


    public StringRoute(String line) {
        this.line = line;
    }


    public Reader getReader() {
        return new StringReader(line);
    }


    public Writer getWriter() {
        stringWriter = new StringWriter();
        return stringWriter;
    }


    public String getResultString() {
        return stringWriter.toString();
    }
}
