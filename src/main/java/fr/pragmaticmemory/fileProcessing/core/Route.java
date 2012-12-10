package fr.pragmaticmemory.fileProcessing.core;

import java.io.Reader;
import java.io.Writer;
public interface Route {

    public Reader getReader() throws Exception;


    public Writer getWriter() throws Exception;
}
