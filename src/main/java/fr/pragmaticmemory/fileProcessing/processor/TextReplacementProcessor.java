package fr.pragmaticmemory.fileProcessing.processor;

public class TextReplacementProcessor extends IndependentLineProcessor {

    private final String sourceString;
    private final String targetString;


    public TextReplacementProcessor(String sourceString, String targetString) {
        this.sourceString = sourceString;
        this.targetString = targetString;
    }


    @Override
    protected String processLine(String line) {
        return line.replace(sourceString, targetString);
    }
}
