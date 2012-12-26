package fr.pragmaticmemory.fileProcessing.fileProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class SingleFileProvider implements FileProvider {

    private final List<File> singleItemlist = new ArrayList<File>();


    public SingleFileProvider(String filePath) {
        this(new File(filePath));
    }


    public SingleFileProvider(File file) {
        singleItemlist.add(file);
    }


    public List<File> getAllFile() {
        return singleItemlist;
    }
}
