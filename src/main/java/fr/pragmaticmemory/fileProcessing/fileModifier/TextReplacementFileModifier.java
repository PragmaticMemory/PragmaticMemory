package fr.pragmaticmemory.fileProcessing.fileModifier;

import fr.pragmaticmemory.fileProcessing.fileProvider.FileProvider;
public class TextReplacementFileModifier extends IndependentLineFileModifier {

    private String sourceString;
    private String targetString;


    public TextReplacementFileModifier(FileProvider fileProvider, String sourceString, String targetString) {
        super(fileProvider);
        this.sourceString = sourceString;
        this.targetString = targetString;
    }


    @Override
    protected String processLine(String line) {
        return line.replace(sourceString, targetString);
    }
}
