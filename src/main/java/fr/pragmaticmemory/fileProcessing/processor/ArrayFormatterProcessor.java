package fr.pragmaticmemory.fileProcessing.processor;

import java.util.List;
public class ArrayFormatterProcessor extends TextProcessor {

    @Override
    protected List<String> processFileContent(List<String> inputLines) throws Exception {
        DataArray dataArray = new DataArray(inputLines);
        return dataArray.buildOutputLines();
    }
}
