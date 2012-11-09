package fr.pragmaticmemory.fileProcessing.fileModifier;

import fr.pragmaticmemory.fileProcessing.utils.FileUtils;
import fr.pragmaticmemory.fileProcessing.fileProvider.FileProvider;
import java.io.File;
import java.util.List;

public abstract class TextFileModifier extends FileModifier {

    protected TextFileModifier(FileProvider fileProvider) {
        super(fileProvider);
    }


    @Override
    protected void process(File file) throws Exception {
        List<String> lines = FileUtils.readLines(file);
        List<String> processedLines = processFileContent(lines);
        FileUtils.writeLines(processedLines, file);
    }


    abstract protected List<String> processFileContent(List<String> lines) throws Exception;
}
