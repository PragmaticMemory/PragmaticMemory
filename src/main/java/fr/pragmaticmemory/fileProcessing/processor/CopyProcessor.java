package fr.pragmaticmemory.fileProcessing.processor;

import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
public class CopyProcessor extends IndependentLineProcessor {

    public CopyProcessor(RouteProvider routeProvider) {
        super(routeProvider);
    }


    @Override
    protected String processLine(String line) {
        return line;
    }
}
