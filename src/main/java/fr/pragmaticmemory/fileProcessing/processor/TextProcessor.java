package fr.pragmaticmemory.fileProcessing.processor;

import fr.pragmaticmemory.fileProcessing.core.Processor;
import fr.pragmaticmemory.fileProcessing.core.Route;
import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
import fr.pragmaticmemory.fileProcessing.utils.FileUtils;
import java.util.List;
public abstract class TextProcessor extends Processor {

    protected TextProcessor(RouteProvider routeProvider) {
        super(routeProvider);
    }


    @Override
    protected void process(int routeIndex) throws Exception {
        final Route route = routeProvider.getRoute(routeIndex);
        List<String> lines = FileUtils.readLines(route.getReader());
        List<String> processedLines = processFileContent(lines);
        FileUtils.writeLines(processedLines, route.getWriter());
    }


    abstract protected List<String> processFileContent(List<String> lines) throws Exception;
}
