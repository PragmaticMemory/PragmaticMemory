package fr.pragmaticmemory.fileProcessing.tool.logAnalyser.core;
import fr.pragmaticmemory.fileProcessing.processor.TextProcessor;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class LogAnalyser extends TextProcessor {
    static private final Pattern DATE_TIME_PATTERN = Pattern.compile("(\\s*\\d+ \\w+ \\d+ \\d+:\\d+:\\d+,\\d+).+");
    static protected final String COLUMN_SEPARATOR = "|";
    protected List<String> outputLines = new ArrayList<String>();


    protected String extractDateTime(String line) {
        Matcher matcher = DATE_TIME_PATTERN.matcher(line);
        matcher.find();
        return matcher.group(1);
    }


    protected void extractDateTime(List<String> lines) {
        for (int i = 0, linesSize = lines.size(); i < linesSize; i++) {
            lines.set(i, extractDateTime(lines.get(i)));
        }
    }
}
