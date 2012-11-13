package fr.pragmaticmemory.fileProcessing.fileModifier;

import fr.pragmaticmemory.fileProcessing.fileProvider.Route;
import fr.pragmaticmemory.fileProcessing.fileProvider.RouteProvider;
import fr.pragmaticmemory.fileProcessing.utils.FileUtils;
import java.util.List;
public abstract class TextModifier extends Modifier {

    protected TextModifier(RouteProvider sourceTargetProvider) {
        super(sourceTargetProvider);
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
