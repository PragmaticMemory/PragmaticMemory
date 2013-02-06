package fr.pragmaticmemory.fileProcessing.processor;

import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
import fr.pragmaticmemory.fileProcessing.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
public class ArrayFormatterProcessor extends TextProcessor {

    static final String ANALYSE_CELL_SEPARATOR = "\\s*\\|\\s*";
    static final String OUTPUT_CELL_SEPARATOR = "|";
    static final String SPACE = " ";


    public ArrayFormatterProcessor(RouteProvider routeProvider) {
        super(routeProvider);
    }


    @Override
    protected List<String> processFileContent(List<String> inputLines) throws Exception {
        List<String[]> rowCellContentList = buildCellContents(inputLines);

        check(inputLines, rowCellContentList);

        Integer[] columnContentSize = getColumnContentSize(rowCellContentList);

        return buildOutputLines(rowCellContentList, columnContentSize);
    }


    private List<String> buildOutputLines(List<String[]> rowCellContentList, Integer[] columnContentSize) {
        List<String> outputLines = new ArrayList<String>();
        for (String[] rowCellContent : rowCellContentList) {
            StringBuilder outputLineBuilder = new StringBuilder();
            outputLineBuilder.append(OUTPUT_CELL_SEPARATOR);
            for (int i = 1, analysedLineLength = rowCellContent.length; i < analysedLineLength; i++) {
                String cellContent = rowCellContent[i];
                outputLineBuilder.append(SPACE);
                outputLineBuilder.append(StringUtils.rightPad(cellContent, columnContentSize[i]));
                outputLineBuilder.append(SPACE);
                outputLineBuilder.append(OUTPUT_CELL_SEPARATOR);
            }
            outputLines.add(outputLineBuilder.toString());
        }
        return outputLines;
    }


    private Integer[] getColumnContentSize(List<String[]> cellContentByLine) {
        Integer size[] = new Integer[cellContentByLine.get(0).length];
        for (int i = 0, sizeLength = size.length; i < sizeLength; i++) {
            size[i] = 0;
        }
        for (String[] analysedLine : cellContentByLine) {
            for (int i = 0, analysedLineLength = analysedLine.length; i < analysedLineLength; i++) {
                if (analysedLine[i].length() > size[i]) {
                    size[i] = analysedLine[i].length();
                }
            }
        }
        return size;
    }


    private void check(List<String> inputLines, List<String[]> cellContentByLine) throws Exception {
        if (inputLines.size() != cellContentByLine.size()) {
            throw new Exception("inputLines.size() != analysedLines.size()");
        }
        int lineNumber = inputLines.size();
        if (lineNumber > 1) {
            for (int i = 1; i < lineNumber; i++) {
                if (cellContentByLine.get(i).length != cellContentByLine.get(i - 1).length) {
                    throw new Exception("Different column number between lines " + (i - 1) + "and " + i);
                }
            }
        }
    }


    private List<String[]> buildCellContents(List<String> inputLines) {
        ArrayList<String[]> cellContentByLine = new ArrayList<String[]>();
        for (String line : inputLines) {
            String[] cells = line.split(ANALYSE_CELL_SEPARATOR);
            if (cells.length == 0) {
                continue;
            }
            cellContentByLine.add(cells);
        }
        return cellContentByLine;
    }
}
