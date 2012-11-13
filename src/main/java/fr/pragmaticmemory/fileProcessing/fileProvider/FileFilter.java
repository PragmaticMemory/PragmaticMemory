package fr.pragmaticmemory.fileProcessing.fileProvider;

import java.io.File;
public interface FileFilter {

    boolean filter(File file);
}
