package fr.pragmaticmemory.fileProcessing.fileProvider;

public class ExclusiveFilter extends AbstractFileProviderFilter {

    public ExclusiveFilter(FileProvider fileProvider, FileFilter fileFilter) {
        super(fileProvider, fileFilter, false);
    }
}
