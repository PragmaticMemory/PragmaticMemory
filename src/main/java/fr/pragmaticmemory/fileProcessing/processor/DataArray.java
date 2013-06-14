package fr.pragmaticmemory.fileProcessing.processor;

import fr.pragmaticmemory.fileProcessing.utils.StringUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class DataArray implements Iterable<ArrayLine> {
    static final String OUTPUT_CELL_SEPARATOR = "|";
    static final String HEADER_OUTPUT_CELL_SEPARATOR = "+";
    static final String HEADER_OUTPUT_LINE = "-";
    static final String SPACE = " ";
    private List<ArrayLine> arrayLineList = new ArrayList<ArrayLine>();
    private List<String[]> cellContents;
    private Integer[] columnContentSize;


    public DataArray(List<String> lineList) {
        for (String lineContent : lineList) {
            arrayLineList.add(new ArrayLine(lineContent));
        }
    }


    public Iterator<ArrayLine> iterator() {
        return new ArrayIterator(arrayLineList.iterator());
    }


    protected List<String[]> buildCellContents() {
        List<String[]> cellContentByLine = new ArrayList<String[]>();
        for (ArrayLine line : arrayLineList) {
            if (line.isSeparator()) {
                continue;
            }
            String[] cells = line.getCellContents();
            if (cells.length == 0) {
                continue;
            }
            cellContentByLine.add(cells);
        }
        return cellContentByLine;
    }


    private Integer[] buildColumnContentSize() throws Exception {
        check();
        List<String[]> cellContentByLine = getCellContents();
        Integer sizeArray[] = new Integer[cellContentByLine.get(0).length];
        for (int i = 0, sizeLength = sizeArray.length; i < sizeLength; i++) {
            sizeArray[i] = 0;
        }
        for (String[] analysedLine : cellContentByLine) {
            for (int i = 0, analysedLineLength = analysedLine.length; i < analysedLineLength; i++) {
                if (analysedLine[i].length() > sizeArray[i]) {
                    sizeArray[i] = analysedLine[i].length();
                }
            }
        }
        return sizeArray;
    }


    private Integer[] getColumnContentSize() throws Exception {
        if (columnContentSize == null) {
            columnContentSize = buildColumnContentSize();
        }
        return columnContentSize;
    }


    public int getDataLineNumber() {
        int dataLineNumber = 0;
        for (ArrayLine arrayLine : arrayLineList) {
            if (arrayLine.isDataLine()) {
                dataLineNumber++;
            }
        }
        return dataLineNumber;
    }


    public void check() throws Exception {
        List<String[]> cellContentByLine = getCellContents();
        int lineNumber = getDataLineNumber();
        if (lineNumber != cellContentByLine.size()) {
            throw new Exception("getDataLineNumber() != getCellContents.size()");
        }
        if (lineNumber > 1) {
            for (int i = 1; i < lineNumber; i++) {
                if (cellContentByLine.get(i).length != cellContentByLine.get(i - 1).length) {
                    throw new Exception("Different column number between lines " + (i - 1) + " and " + i);
                }
            }
        }
    }


    private List<String[]> getCellContents() {
        if (cellContents == null) {
            cellContents = buildCellContents();
        }
        return cellContents;
    }


    public List<String> buildOutputLines() throws Exception {
        List<String> outputLines = new ArrayList<String>();
        String separatorLine = getSeparatorLine();
        outputLines.add(separatorLine);
        for (int lineIndex = 0, rowCellContentListSize = getCellContents().size();
             lineIndex < rowCellContentListSize;
             lineIndex++) {
            if (lineIndex == 1) {
                outputLines.add(separatorLine);
            }
            StringBuilder outputLineBuilder = new StringBuilder();
            String[] rowCellContent = getCellContents().get(lineIndex);
            outputLineBuilder.append(OUTPUT_CELL_SEPARATOR);
            for (int cellIndex = 0, analysedLineLength = rowCellContent.length;
                 cellIndex < analysedLineLength;
                 cellIndex++) {
                String cellContent = rowCellContent[cellIndex];
                outputLineBuilder.append(SPACE);
                outputLineBuilder.append(StringUtils.rightPad(cellContent, getColumnContentSize()[cellIndex]));
                outputLineBuilder.append(SPACE);
                outputLineBuilder.append(OUTPUT_CELL_SEPARATOR);
            }

            outputLines.add(outputLineBuilder.toString());
        }
        outputLines.add(separatorLine);
        return outputLines;
    }


    private String getSeparatorLine() throws Exception {
        StringBuilder separatorLineBuilder = new StringBuilder();
        separatorLineBuilder.append(HEADER_OUTPUT_CELL_SEPARATOR);
        for (Integer size : getColumnContentSize()) {
            for (int j = 0; j < size + 2; j++) {
                separatorLineBuilder.append(HEADER_OUTPUT_LINE);
            }
            separatorLineBuilder.append(HEADER_OUTPUT_CELL_SEPARATOR);
        }
        return separatorLineBuilder.toString();
    }
}
