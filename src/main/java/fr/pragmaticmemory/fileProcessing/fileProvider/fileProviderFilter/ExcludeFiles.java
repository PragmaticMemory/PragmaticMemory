package fr.pragmaticmemory.fileProcessing.fileProvider.fileProviderFilter;

import fr.pragmaticmemory.fileProcessing.fileProvider.FileProvider;
public class ExcludeFiles extends AbstractFileProviderFilter {

    public ExcludeFiles(FileProvider fileProvider, FileFilter fileFilter) {
        super(fileProvider, fileFilter, false);
    }
}
