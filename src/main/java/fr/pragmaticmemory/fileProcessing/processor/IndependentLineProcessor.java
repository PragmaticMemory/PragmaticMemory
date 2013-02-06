package fr.pragmaticmemory.fileProcessing.processor;

import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
import java.util.ArrayList;
import java.util.List;
public abstract class IndependentLineProcessor extends TextProcessor {

    protected IndependentLineProcessor(RouteProvider routeProvider) {
        super(routeProvider);
    }


    @Override
    protected List<String> processFileContent(List<String> inputLines) throws Exception {
        final List<String> resultList = new ArrayList<String>();
        for (int i = 0, linesSize = inputLines.size(); i < linesSize; i++) {
            resultList.add(processLine(inputLines.get(i)));
        }
        return resultList;
    }


    protected abstract String processLine(String line);
}
