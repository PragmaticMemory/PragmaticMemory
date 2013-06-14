package fr.pragmaticmemory.fileProcessing.processor.arrayFormatter;

import fr.pragmaticmemory.fileProcessing.processor.TextProcessor;
import fr.pragmaticmemory.fileProcessing.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
public class ArrayFormatterProcessor extends TextProcessor {
    static final String OUTPUT_CELL_SEPARATOR = "|";
    static final String HEADER_OUTPUT_CELL_SEPARATOR = "+";
    static final String HEADER_OUTPUT_LINE = "-";
    static final String SPACE = " ";

    @Override
    protected List<String> processFileContent(List<String> inputLines) throws Exception {
        DataArray dataArray = new DataArray(inputLines);
        return buildOutputLines(dataArray);
    }


    public List<String> buildOutputLines(DataArray dataArray) throws Exception {
        List<String> outputLines = new ArrayList<String>();
        String separatorLine = getSeparatorLine(dataArray);
        for (ArrayLine arrayLine : dataArray) {
            if (arrayLine.isSeparator()) {
                outputLines.add(separatorLine);
                continue;
            }
            StringBuilder outputLineBuilder = new StringBuilder();
            outputLineBuilder.append(OUTPUT_CELL_SEPARATOR);
            for (int cellIndex = 0, analysedLineLength = arrayLine.getCellsNumber();
                 cellIndex < analysedLineLength;
                 cellIndex++) {
                String cellContent = arrayLine.getCellContent(cellIndex);
                outputLineBuilder.append(SPACE);
                outputLineBuilder.append(StringUtils.rightPad(cellContent,
                                                              dataArray.getColumnContentSize()[cellIndex]));
                outputLineBuilder.append(SPACE);
                outputLineBuilder.append(OUTPUT_CELL_SEPARATOR);
            }

            outputLines.add(outputLineBuilder.toString());
        }
        return outputLines;
    }


    public String getSeparatorLine(DataArray dataArray) throws Exception {
        StringBuilder separatorLineBuilder = new StringBuilder();
        separatorLineBuilder.append(HEADER_OUTPUT_CELL_SEPARATOR);
        for (Integer size : dataArray.getColumnContentSize()) {
            for (int j = 0; j < size + 2; j++) {
                separatorLineBuilder.append(HEADER_OUTPUT_LINE);
            }
            separatorLineBuilder.append(HEADER_OUTPUT_CELL_SEPARATOR);
        }
        return separatorLineBuilder.toString();
    }
}
