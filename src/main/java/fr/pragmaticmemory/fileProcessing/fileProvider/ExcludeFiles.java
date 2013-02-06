package fr.pragmaticmemory.fileProcessing.fileProvider;

public class ExcludeFiles extends AbstractFileProviderFilter {

    public ExcludeFiles(FileProvider fileProvider, FileFilter fileFilter) {
        super(fileProvider, fileFilter, false);
    }
}
