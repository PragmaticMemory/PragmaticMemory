package fr.pragmaticmemory.fileProcessing.routeProvider;

import fr.pragmaticmemory.fileProcessing.core.Route;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
public class FileRoute implements Route {

    private File sourceFile;
    private File destinationFile;


    public FileRoute(File sourceFile, File destinationFile) {
        this.sourceFile = sourceFile;
        this.destinationFile = destinationFile;
    }


    public Reader getReader() throws Exception {
        return new FileReader(sourceFile);
    }


    public Writer getWriter() throws Exception {
        return new FileWriter(destinationFile);
    }
}
