package fr.pragmaticmemory.fileProcessing.processor;

import fr.pragmaticmemory.fileProcessing.utils.StringUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ArrayLine {
    static final String ANALYSE_CELL_SEPARATOR = "\\|";
    private String content;


    public ArrayLine(String content) {
        this.content = content;
    }


    public String getContent() {
        return content;
    }


    protected boolean isSeparator() {
        Pattern pattern = Pattern.compile("(\\+-+)\\+");
        final Matcher matcher = pattern.matcher(content);
        return matcher.find();
    }


    public String[] getCellContents() {
        String[] cells = StringUtils.split(content, ANALYSE_CELL_SEPARATOR);
        trim(cells);
        return cells;
    }


    private void trim(String[] stringArray) {
        for (int i = 0, cellsLength = stringArray.length; i < cellsLength; i++) {
            stringArray[i] = stringArray[i].trim();
        }
    }
}
