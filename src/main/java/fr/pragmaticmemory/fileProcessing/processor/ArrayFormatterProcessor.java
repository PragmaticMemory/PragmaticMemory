package fr.pragmaticmemory.fileProcessing.processor;

import java.util.List;
public class ArrayFormatterProcessor extends TextProcessor {

    @Override
    protected List<String> processFileContent(List<String> inputLines) throws Exception {
        Array array = new Array(inputLines);
        return array.buildOutputLines();
    }
}
