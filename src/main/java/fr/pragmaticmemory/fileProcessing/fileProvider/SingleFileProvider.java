package fr.pragmaticmemory.fileProcessing.fileProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class SingleFileProvider extends FileProvider {

    private List<File> singleItemlist = new ArrayList<File>();


    public SingleFileProvider(String filePath) {
        singleItemlist.add(new File(filePath));
    }


    public SingleFileProvider(File file) {
        singleItemlist.add(file);
    }


    @Override
    public List<File> getAllFile() {
        return singleItemlist;
    }


    @Override
    protected boolean keepFile(File childFile) {
        return true;
    }
}
