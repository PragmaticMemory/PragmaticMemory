package fr.pragmaticmemory.fileProcessing.processor.arrayFormatter;

import fr.pragmaticmemory.fileProcessing.processor.TextProcessor;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class SubversionLogProcessor extends TextProcessor {
    static final String OUTPUT_CELL_SEPARATOR = "|";
    static private final Pattern AUTOR_PATTERN = Pattern.compile("Author: (\\w+)");
    static private final Pattern DATE_PATTERN = Pattern.compile("Date: (\\d+:\\d+:\\d+, .+)");
    static final String SEPARATOR = "|";

    private String author;
    private String date;


    @Override
    protected List<String> processFileContent(List<String> inputLines) throws Exception {
        StringBuilder messageBuilder = new StringBuilder();
        List<String> outputList = new ArrayList<String>();
        for (String line : inputLines) {

            Matcher authorMatcher = AUTOR_PATTERN.matcher(line);
            if (authorMatcher.find()) {
                author = authorMatcher.group(1);
                continue;
            }

            Matcher dateMatcher = DATE_PATTERN.matcher(line);
            if (dateMatcher.find()) {
                date = dateMatcher.group(1);
                continue;
            }
            if (line.startsWith("Message:")) {
                messageBuilder = new StringBuilder();
                continue;
            }

            if ("".equals(line) || line.startsWith("Modified :") || line.startsWith("Deleted :")
                || line.startsWith("Replacing :") || line.startsWith("Removed file/folder") ||
                line.startsWith("Added :") || line.startsWith("Revision:") || line.startsWith("Message:")) {

                continue;
            }

            if ("----".equals(line)) {
                outputList.add(author + SEPARATOR + date + SEPARATOR + messageBuilder.toString());
                messageBuilder = new StringBuilder();
                continue;
            }

            messageBuilder.append(line);
        }
        return outputList;
    }
}
