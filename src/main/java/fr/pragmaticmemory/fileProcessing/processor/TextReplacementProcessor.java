package fr.pragmaticmemory.fileProcessing.processor;

import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
public class TextReplacementProcessor extends IndependentLineProcessor {

    private String sourceString;
    private String targetString;


    public TextReplacementProcessor(RouteProvider routeProvider, String sourceString, String targetString) {
        super(routeProvider);
        this.sourceString = sourceString;
        this.targetString = targetString;
    }


    @Override
    protected String processLine(String line) {
        return line.replace(sourceString, targetString);
    }
}
