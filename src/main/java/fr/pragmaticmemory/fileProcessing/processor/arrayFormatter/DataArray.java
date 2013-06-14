package fr.pragmaticmemory.fileProcessing.processor.arrayFormatter;

import fr.pragmaticmemory.fileProcessing.processor.arrayFormatter.ArrayIterator;
import fr.pragmaticmemory.fileProcessing.processor.arrayFormatter.ArrayLine;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class DataArray implements Iterable<ArrayLine> {
    private List<ArrayLine> arrayLineList = new ArrayList<ArrayLine>();
    private List<ArrayLine> dataArrayLines;
    private Integer[] columnContentSize;


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


    private Integer[] buildColumnContentSize() throws Exception {
        checkColumsNumber();
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


    private void checkColumsNumber() throws Exception {
        int dataLineNumber = getDataArrayLines().size();
        if (dataLineNumber > 1) {
            for (int i = 1; i < dataLineNumber; i++) {
                if (getDataArrayLines().get(i).getCellsNumber() != getDataArrayLines().get(i - 1).getCellsNumber()) {
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
}
