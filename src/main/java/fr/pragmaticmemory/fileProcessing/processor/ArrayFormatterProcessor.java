package fr.pragmaticmemory.fileProcessing.processor;

import fr.pragmaticmemory.fileProcessing.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
public class ArrayFormatterProcessor extends TextProcessor {

    static final String ANALYSE_CELL_SEPARATOR = "\\|";
    static final String OUTPUT_CELL_SEPARATOR = "|";
    static final String HEADER_OUTPUT_CELL_SEPARATOR = "+";
    static final String HEADER_OUTPUT_LINE = "-";
    static final String SPACE = " ";


    @Override
    protected List<String> processFileContent(List<String> inputLines) throws Exception {
        inputLines.remove(0);
        inputLines.remove(1);
        inputLines.remove(inputLines.size() - 1);
        List<String[]> rowCellContentList = buildCellContents(inputLines);

        trimCellContent(rowCellContentList);

        check(inputLines, rowCellContentList);

        Integer[] columnContentSize = getColumnContentSize(rowCellContentList);

        return buildOutputLines(rowCellContentList, columnContentSize);
    }


    private void trimCellContent(List<String[]> rowCellContentList) {
        for (String[] rowCellContent : rowCellContentList) {
            for (int i = 0, rowCellContentLength = rowCellContent.length; i < rowCellContentLength; i++) {
                rowCellContent[i] = rowCellContent[i].trim();
            }
        }
    }


    private List<String> buildOutputLines(List<String[]> rowCellContentList, Integer[] columnContentSize) {
        List<String> outputLines = new ArrayList<String>();
        String separatorLine = getSeparatorLine(columnContentSize);
        outputLines.add(separatorLine);
        for (int lineIndex = 0, rowCellContentListSize = rowCellContentList.size();
             lineIndex < rowCellContentListSize;
             lineIndex++) {
            if (lineIndex == 1) {
                outputLines.add(separatorLine);
            }
            StringBuilder outputLineBuilder = new StringBuilder();
            String[] rowCellContent = rowCellContentList.get(lineIndex);
            outputLineBuilder.append(OUTPUT_CELL_SEPARATOR);
            for (int cellIndex = 1, analysedLineLength = rowCellContent.length - 1;
                 cellIndex < analysedLineLength;
                 cellIndex++) {
                String cellContent = rowCellContent[cellIndex];
                outputLineBuilder.append(SPACE);
                outputLineBuilder.append(StringUtils.rightPad(cellContent, columnContentSize[cellIndex]));
                outputLineBuilder.append(SPACE);
                outputLineBuilder.append(OUTPUT_CELL_SEPARATOR);
            }

            outputLines.add(outputLineBuilder.toString());
        }
        outputLines.add(separatorLine);
        return outputLines;
    }


    private String getSeparatorLine(Integer[] columnContentSize) {
        StringBuilder separatorLineBuilder = new StringBuilder();
        separatorLineBuilder.append(HEADER_OUTPUT_CELL_SEPARATOR);
        for (int columnIndex = 1, columnContentSizeLength = columnContentSize.length - 1;
             columnIndex < columnContentSizeLength;
             columnIndex++) {
            for (int j = 0; j < columnContentSize[columnIndex] + 2; j++) {
                separatorLineBuilder.append(HEADER_OUTPUT_LINE);
            }
            separatorLineBuilder.append(HEADER_OUTPUT_CELL_SEPARATOR);
        }
        return separatorLineBuilder.toString();
    }


    private boolean isLineSeparatorIndex(List<String[]> rowCellContentList, int lineIndex) {
        return lineIndex == 0 || lineIndex == 2 || lineIndex == rowCellContentList.size() - 1;
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
                    throw new Exception("Different column number between lines " + (i - 1) + " and " + i);
                }
            }
        }
    }


    private List<String[]> buildCellContents(List<String> inputLines) {
        List<String[]> cellContentByLine = new ArrayList<String[]>();
        for (String line : inputLines) {
            String[] cells = StringUtils.split(line, ANALYSE_CELL_SEPARATOR);
            if (cells.length == 0) {
                continue;
            }
            cellContentByLine.add(cells);
        }
        return cellContentByLine;
    }
}
