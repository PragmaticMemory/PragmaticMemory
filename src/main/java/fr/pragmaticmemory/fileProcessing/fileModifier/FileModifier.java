package fr.pragmaticmemory.fileProcessing.fileModifier;

import fr.pragmaticmemory.fileProcessing.fileProvider.FileProvider;
import java.io.File;
import java.util.List;
public abstract class FileModifier {

    protected FileProvider fileProvider;


    protected FileModifier(FileProvider fileProvider) {
        this.fileProvider = fileProvider;
    }
                 

    public void process() throws Exception {
        List<File> fileList = fileProvider.getAllFile();
        for (File file : fileList) {
            process(file);
        }
    }


    abstract protected void process(File file) throws Exception;
}
