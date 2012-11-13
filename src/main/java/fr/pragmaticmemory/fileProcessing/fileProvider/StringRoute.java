package fr.pragmaticmemory.fileProcessing.fileProvider;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
public class StringRoute implements Route {

    private String inputString;
    private StringWriter stringWriter;


    public StringRoute(String inputString) {
        this.inputString = inputString;
    }


    public Reader getReader() {
        return new StringReader(inputString);
    }


    public Writer getWriter() {
        stringWriter = new StringWriter();
        return stringWriter;
    }


    public String getOutputString() {
        return stringWriter.toString();
    }
}
