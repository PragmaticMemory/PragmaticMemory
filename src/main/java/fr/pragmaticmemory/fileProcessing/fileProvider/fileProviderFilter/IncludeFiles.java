package fr.pragmaticmemory.fileProcessing.fileProvider.fileProviderFilter;

import fr.pragmaticmemory.fileProcessing.fileProvider.FileProvider;
public class IncludeFiles extends AbstractFileProviderFilter {

    public IncludeFiles(FileProvider fileProvider, FileFilter fileFilter) {
        super(fileProvider, fileFilter, true);
    }
}
