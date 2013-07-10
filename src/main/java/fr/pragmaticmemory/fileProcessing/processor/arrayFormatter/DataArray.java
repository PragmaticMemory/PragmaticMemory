package fr.pragmaticmemory.fileProcessing.processor.arrayFormatter;

import fr.pragmaticmemory.fileProcessing.utils.StringUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class DataArray implements Iterable<ArrayLine> {
    static final String SPACE = " ";
    private List<ArrayLine> arrayLineList = new ArrayList<ArrayLine>();
    private List<Integer> columnContentSize = new ArrayList<Integer>();

    static private final String HEADER_OUTPUT_CELL_SEPARATOR = "+";
    static private final String HEADER_OUTPUT_LINE = "-";
    private String separatorLine = null;


    public DataArray(List<String> lineList) throws Exception {
        for (String lineContent : lineList) {
            add(new ArrayLine(lineContent));
        }
    }


    private void add(ArrayLine arrayLine) throws Exception {
        arrayLineList.add(arrayLine);
        if (arrayLine.isSeparator()) {
            return;
        }
        String[] cellContents = arrayLine.getCellContents();
        for (int columnIndex = 0, cellContentsLength = cellContents.length;
             columnIndex < cellContentsLength;
             columnIndex++) {
            String cellContent = cellContents[columnIndex];
            if (columnContentSize.size() < columnIndex + 1) {
                columnContentSize.add(cellContent.length());
            }
            else {
                Integer oldSize = columnContentSize.get(columnIndex);
                if (cellContent.length() > oldSize) {
                    columnContentSize.set(columnIndex, cellContent.length());
                }
            }
        }
        separatorLine = buildSeparatorLine();
    }


    public Iterator<ArrayLine> iterator() {
        return new ArrayIterator(arrayLineList.iterator());
    }


    public String getPaddedColumnContent(ArrayLine arrayLine, int columnIndex) throws Exception {
        if (columnContentSize.get(columnIndex) == 0) {
            return SPACE;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(SPACE);
        builder.append(StringUtils.rightPad(arrayLine.getCellContent(columnIndex), columnContentSize.get(columnIndex)));
        builder.append(SPACE);
        return builder.toString();
    }


    public String getSeparatorLine() throws Exception {
        return separatorLine;
    }


    private int getPaddedColumnContentSize(int columnIndex) throws Exception {
        if (columnContentSize.get(columnIndex) == 0) {
            return SPACE.length();
        }
        return SPACE.length() + columnContentSize.get(columnIndex) + SPACE.length();
    }


    private String buildSeparatorLine() throws Exception {
        StringBuilder separatorLineBuilder = new StringBuilder();
        separatorLineBuilder.append(HEADER_OUTPUT_CELL_SEPARATOR);
        for (int columnIndex = 0; columnIndex < columnContentSize.size(); columnIndex++) {
            for (int j = 0; j < getPaddedColumnContentSize(columnIndex); j++) {
                separatorLineBuilder.append(HEADER_OUTPUT_LINE);
            }
            separatorLineBuilder.append(HEADER_OUTPUT_CELL_SEPARATOR);
        }
        return separatorLineBuilder.toString();
    }
}
