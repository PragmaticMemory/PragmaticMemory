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
    private Integer[] columnContentSize;


    public DataArray(List<String> lineList) {
        for (String lineContent : lineList) {
            arrayLineList.add(new ArrayLine(lineContent));
        }
    }


    public Iterator<ArrayLine> iterator() {
        return new ArrayIterator(arrayLineList.iterator());
    }


    private Integer[] buildColumnContentSize() throws Exception {
        check();
        List<ArrayLine> dataArrayLineList = getDataArrayLines();
        Integer sizeArray[] = new Integer[dataArrayLineList.get(0).getCellsNumber()];
        for (int cellIndex = 0, cellsNumber = sizeArray.length; cellIndex < cellsNumber; cellIndex++) {
            sizeArray[cellIndex] = 0;
        }

        for (ArrayLine dataArrayLine : dataArrayLineList) {
            for (int i = 0, cellsNumber = dataArrayLine.getCellsNumber(); i < cellsNumber; i++) {
                if (dataArrayLine.getCellContent(i).length() > sizeArray[i]) {
                    sizeArray[i] = dataArrayLine.getCellContent(i).length();
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


    private int getDataLineNumber() {
        int dataLineNumber = 0;
        for (ArrayLine arrayLine : arrayLineList) {
            if (arrayLine.isDataLine()) {
                dataLineNumber++;
            }
        }
        return dataLineNumber;
    }


    private void check() throws Exception {
        List<ArrayLine> dataArrayLines = getDataArrayLines();
        int dataLineNumber = getDataLineNumber();

        if (dataLineNumber > 1) {
            for (int i = 1; i < dataLineNumber; i++) {
                if (dataArrayLines.get(i).getCellsNumber() != dataArrayLines.get(i - 1).getCellsNumber()) {
                    throw new Exception("Different column number between lines " + (i - 1) + " and " + i);
                }
            }
        }
    }


    private List<ArrayLine> getDataArrayLines() {
        List<ArrayLine> dataArrayLineList = new ArrayList<ArrayLine>();
        for (ArrayLine arrayLine : arrayLineList) {
            if (arrayLine.isDataLine()) {
                dataArrayLineList.add(arrayLine);
            }
        }
        return dataArrayLineList;
    }


    public List<String> buildOutputLines() throws Exception {
        List<String> outputLines = new ArrayList<String>();
        String separatorLine = getSeparatorLine();
        for (ArrayLine arrayLine : arrayLineList) {
            if (arrayLine.isSeparator()) {
                outputLines.add(separatorLine);
                continue;
            }
            StringBuilder outputLineBuilder = new StringBuilder();
            String[] rowCellContent = arrayLine.getCellContents();
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
