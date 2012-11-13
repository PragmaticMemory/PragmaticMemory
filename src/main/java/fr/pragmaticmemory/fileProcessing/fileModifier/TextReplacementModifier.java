package fr.pragmaticmemory.fileProcessing.fileModifier;

import fr.pragmaticmemory.fileProcessing.fileProvider.RouteProvider;
public class TextReplacementModifier extends IndependentLineModifier {

    private String sourceString;
    private String targetString;


    public TextReplacementModifier(RouteProvider routeProvider, String sourceString, String targetString) {
        super(routeProvider);
        this.sourceString = sourceString;
        this.targetString = targetString;
    }


    @Override
    protected String processLine(String line) {
        return line.replace(sourceString, targetString);
    }
}
