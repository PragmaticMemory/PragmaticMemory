package fr.pragmaticmemory.fileProcessing.fileProvider.fileProviderFilter;

import java.io.File;
public interface FileFilter {

    boolean filter(File file);
}
