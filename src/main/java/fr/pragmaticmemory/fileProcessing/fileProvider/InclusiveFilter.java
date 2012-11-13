package fr.pragmaticmemory.fileProcessing.fileProvider;

public class InclusiveFilter extends AbstractFileProviderFilter {

    public InclusiveFilter(FileProvider fileProvider, FileFilter fileFilter) {
        super(fileProvider, fileFilter, true);
    }
}
