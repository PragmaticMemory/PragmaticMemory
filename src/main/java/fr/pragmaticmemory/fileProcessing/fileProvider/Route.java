package fr.pragmaticmemory.fileProcessing.fileProvider;

import java.io.Reader;
import java.io.Writer;
public interface Route {

    public Reader getReader() throws Exception;


    public Writer getWriter() throws Exception;
}
