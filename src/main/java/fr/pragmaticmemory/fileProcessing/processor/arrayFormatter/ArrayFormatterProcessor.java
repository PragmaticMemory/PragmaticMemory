package fr.pragmaticmemory.fileProcessing.processor.arrayFormatter;

import fr.pragmaticmemory.fileProcessing.processor.TextProcessor;
import java.util.ArrayList;
import java.util.List;
public class ArrayFormatterProcessor extends TextProcessor {
    static final String OUTPUT_CELL_SEPARATOR = "|";


    @Override
    protected List<String> processFileContent(List<String> inputLines) throws Exception {
        DataArray dataArray = new DataArray(inputLines);
        return buildOutputLines(dataArray);
    }


    public List<String> buildOutputLines(DataArray dataArray) throws Exception {
        List<String> outputLines = new ArrayList<String>();
        for (ArrayLine arrayLine : dataArray) {
            if (arrayLine.isSeparator()) {
                outputLines.add(dataArray.getSeparatorLine());
                continue;
            }
            StringBuilder outputLineBuilder = new StringBuilder();
            outputLineBuilder.append(OUTPUT_CELL_SEPARATOR);
            for (int cellIndex = 0, analysedLineLength = arrayLine.getCellNumber();
                 cellIndex < analysedLineLength;
                 cellIndex++) {
                outputLineBuilder.append(dataArray.getPaddedColumnContent(arrayLine, cellIndex));
                outputLineBuilder.append(OUTPUT_CELL_SEPARATOR);
            }

            outputLines.add(outputLineBuilder.toString());
        }
        return outputLines;
    }
}
