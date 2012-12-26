package fr.pragmaticmemory.fileProcessing.fileProvider;

import java.io.File;
public class ExtensionFileFiler implements FileFilter {

    private final String extension;


    public ExtensionFileFiler(String extension) {
        this.extension = extension;
    }


    public boolean filter(File file) {
        final String absolutePath = file.getAbsolutePath();
        return absolutePath.endsWith(extension);
    }
}
