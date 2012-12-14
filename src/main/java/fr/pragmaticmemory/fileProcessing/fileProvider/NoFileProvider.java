package fr.pragmaticmemory.fileProcessing.fileProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class NoFileProvider implements FileProvider {
    public List<File> getAllFile() {
        return new ArrayList<File>();
    }
}
