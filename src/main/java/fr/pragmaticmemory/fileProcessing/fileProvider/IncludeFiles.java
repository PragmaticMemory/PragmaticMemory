package fr.pragmaticmemory.fileProcessing.fileProvider;

public class IncludeFiles extends AbstractFileProviderFilter {

    public IncludeFiles(FileProvider fileProvider, FileFilter fileFilter) {
        super(fileProvider, fileFilter, true);
    }
}
