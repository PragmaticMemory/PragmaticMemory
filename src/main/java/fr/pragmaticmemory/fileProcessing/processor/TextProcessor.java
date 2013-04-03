package fr.pragmaticmemory.fileProcessing.processor;

import fr.pragmaticmemory.fileProcessing.core.Processor;
import fr.pragmaticmemory.fileProcessing.core.Route;
import fr.pragmaticmemory.fileProcessing.utils.FileUtils;
import java.util.List;
public abstract class TextProcessor extends Processor {

    @Override
    protected void process(Route route) throws Exception {
        final List<String> lines = FileUtils.readLines(route.getReader());
        final List<String> processedLines = processFileContent(lines);
        FileUtils.writeLines(processedLines, route.getWriter());
    }


    abstract protected List<String> processFileContent(List<String> inputLines) throws Exception;
}
