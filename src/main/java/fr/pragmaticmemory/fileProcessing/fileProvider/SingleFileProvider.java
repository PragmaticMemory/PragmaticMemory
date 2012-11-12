package fr.pragmaticmemory.fileProcessing.fileProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class SingleFileProvider extends FileProvider {

    private List<File> singleItemlist = new ArrayList<File>();


    public SingleFileProvider(String filePath) {
        this(new File(filePath));
    }


    public SingleFileProvider(File file) {
        singleItemlist.add(file);
    }


    @Override
    public List<File> getAllFile() {
        return singleItemlist;
    }
}
