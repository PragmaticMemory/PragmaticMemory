package fr.pragmaticmemory.fileProcessing.processor;

import fr.pragmaticmemory.fileProcessing.utils.StringUtils;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ArrayLine {
    static final String ANALYSE_CELL_SEPARATOR = "\\|";
    static private final Pattern PATTERN = Pattern.compile("(\\+-+)\\+");
    private String content;


    public ArrayLine(String content) {
        this.content = content;
    }


    public String getContent() {
        return content;
    }


    protected boolean isSeparator() {
        final Matcher matcher = PATTERN.matcher(content);
        return matcher.find();
    }


    protected boolean isDataLine() {
        return !isSeparator();
    }


    public String[] getCellContents() {
        String[] cells = StringUtils.split(content, ANALYSE_CELL_SEPARATOR);
        trim(cells);
        return Arrays.copyOfRange(cells, 1, cells.length-1);
    }


    private void trim(String[] stringArray) {
        for (int i = 0, cellsLength = stringArray.length; i < cellsLength; i++) {
            stringArray[i] = stringArray[i].trim();
        }
    }
}
