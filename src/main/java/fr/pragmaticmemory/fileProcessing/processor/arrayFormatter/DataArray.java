package fr.pragmaticmemory.fileProcessing.processor.arrayFormatter;

import fr.pragmaticmemory.fileProcessing.utils.StringUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class DataArray implements Iterable<ArrayLine> {
    static final String SPACE = " ";
    private List<ArrayLine> arrayLineList = new ArrayList<ArrayLine>();
    private List<ArrayLine> dataArrayLines;
    private Integer[] columnContentSize;

    static private final String OUTPUT_CELL_SEPARATOR = "|";
    static private final String HEADER_OUTPUT_CELL_SEPARATOR = "+";
    static private final String HEADER_OUTPUT_LINE = "-";
    static private String separatorLine = null;


    public DataArray(List<String> lineList) {
        for (String lineContent : lineList) {
            arrayLineList.add(new ArrayLine(lineContent));
        }
    }


    public Iterator<ArrayLine> iterator() {
        return new ArrayIterator(arrayLineList.iterator());
    }


    public Integer[] getColumnContentSize() throws Exception {
        if (columnContentSize == null) {
            columnContentSize = buildColumnContentSize();
        }
        return columnContentSize;
    }


    public int getColumNumber() throws Exception {
        return getColumnContentSize().length;
    }


    private Integer getColumnContentSize(int columnIndex) throws Exception {
        return getColumnContentSize()[columnIndex];
    }


    public String getPaddedColumnContent(ArrayLine arrayLine, int columnIndex) throws Exception {
        if (getColumnContentSize(columnIndex) == 0) {
            return SPACE;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(SPACE);
        builder.append(StringUtils.rightPad(arrayLine.getCellContent(columnIndex), getColumnContentSize(columnIndex)));
        builder.append(SPACE);
        return builder.toString();
    }


    public int getPaddedColumnContentSize(int columnIndex) throws Exception {
        if (getColumnContentSize(columnIndex) == 0) {
            return 1;
        }
        return SPACE.length() + getColumnContentSize(columnIndex) + SPACE.length();
    }


    private Integer[] buildColumnContentSize() throws Exception {
        checkColumsNumber();
        List<ArrayLine> dataArrayLineList = getDataArrayLines();
        Integer sizeArray[] = new Integer[dataArrayLineList.get(0).getCellNumber()];
        for (int columnIndex = 0, columnNumber = sizeArray.length; columnIndex < columnNumber; columnIndex++) {
            sizeArray[columnIndex] = 0;
        }

        for (ArrayLine dataArrayLine : dataArrayLineList) {
            for (int i = 0, columNumber = dataArrayLine.getCellNumber(); i < columNumber; i++) {
                if (dataArrayLine.getCellContent(i).length() > sizeArray[i]) {
                    sizeArray[i] = dataArrayLine.getCellContent(i).length();
                }
            }
        }
        return sizeArray;
    }


    private void checkColumsNumber() throws Exception {
        int dataLineNumber = getDataArrayLines().size();
        if (dataLineNumber > 1) {
            for (int i = 1; i < dataLineNumber; i++) {
                if (getDataArrayLines().get(i).getCellNumber() != getDataArrayLines().get(i - 1).getCellNumber()) {
                    throw new Exception("Different column number between lines " + (i - 1) + " and " + i);
                }
            }
        }
    }


    private List<ArrayLine> getDataArrayLines() {
        if (dataArrayLines == null) {
            dataArrayLines = buildDataArrayLines();
        }
        return dataArrayLines;
    }


    private List<ArrayLine> buildDataArrayLines() {
        List<ArrayLine> dataArrayLineList = new ArrayList<ArrayLine>();
        for (ArrayLine arrayLine : arrayLineList) {
            if (arrayLine.isDataLine()) {
                dataArrayLineList.add(arrayLine);
            }
        }
        return dataArrayLineList;
    }


    synchronized public String getSeparatorLine() throws Exception {
        if (separatorLine == null) {
            separatorLine = buildSeparatorLine();
        }
        return separatorLine;
    }


    public String buildSeparatorLine() throws Exception {
        StringBuilder separatorLineBuilder = new StringBuilder();
        separatorLineBuilder.append(HEADER_OUTPUT_CELL_SEPARATOR);
        for (int columnIndex = 0; columnIndex < getColumNumber(); columnIndex++) {
            for (int j = 0; j < getPaddedColumnContentSize(columnIndex); j++) {
                separatorLineBuilder.append(HEADER_OUTPUT_LINE);
            }
            separatorLineBuilder.append(HEADER_OUTPUT_CELL_SEPARATOR);
        }
        return separatorLineBuilder.toString();
    }
}
