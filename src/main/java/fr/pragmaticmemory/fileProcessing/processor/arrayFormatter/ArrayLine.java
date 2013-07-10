package fr.pragmaticmemory.fileProcessing.processor.arrayFormatter;

import fr.pragmaticmemory.fileProcessing.utils.StringUtils;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ArrayLine {
    static private final String CELL_SEPARATOR = "\\|";
    static private final Pattern SEPARATOR_PATTERN = Pattern.compile("^\\s*(\\+-+)+\\+\\s*$");
    private String lineContent;
    private String[] cellContents;


    public ArrayLine(String lineContent) {
        this.lineContent = lineContent;
    }


    public boolean isSeparator() {
        final Matcher matcher = SEPARATOR_PATTERN.matcher(lineContent);
        return matcher.find();
    }


    public boolean isDataLine() {
        return !isSeparator();
    }


    public int getCellNumber() {
        return getCellContents().length;
    }


    public String getCellContent(int cellIndex) {
        return getCellContents()[cellIndex];
    }


    public String[] getCellContents() {
        if (cellContents == null) {
            cellContents = buildCellContents();
        }
        return cellContents;
    }


    private String[] buildCellContents() {
        String[] extractedCellContents = StringUtils.split(lineContent, CELL_SEPARATOR);
        trim(extractedCellContents);
        return Arrays.copyOfRange(extractedCellContents, 1, extractedCellContents.length - 1);
    }


    private void trim(String[] stringArray) {
        for (int i = 0, cellsLength = stringArray.length; i < cellsLength; i++) {
            stringArray[i] = stringArray[i].trim();
        }
    }
}
