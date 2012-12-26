package fr.pragmaticmemory.fileProcessing.fileProvider;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AbstractFileProviderFilter implements FileProvider {

    private final FileProvider fileProvider;
    private final FileFilter fileFilter;
    private final boolean inclusive;


    public AbstractFileProviderFilter(FileProvider fileProvider, FileFilter fileFilter, boolean isInclusive) {
        this.fileProvider = fileProvider;
        this.fileFilter = fileFilter;
        inclusive = isInclusive;
    }


    public List<File> getAllFile() {
        final List<File> filteredFileList = new ArrayList<File>();
        final List<File> allFileList = fileProvider.getAllFile();
        for (File file : allFileList) {
            if (fileFilter.filter(file) && inclusive) {
                filteredFileList.add(file);
            }
        }
        return filteredFileList;
    }
}
