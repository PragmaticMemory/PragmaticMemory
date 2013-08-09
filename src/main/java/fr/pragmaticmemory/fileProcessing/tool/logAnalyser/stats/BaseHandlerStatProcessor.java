package fr.pragmaticmemory.fileProcessing.tool.logAnalyser.stats;

import fr.pragmaticmemory.fileProcessing.tool.logAnalyser.core.LogAnalyser;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class BaseHandlerStatProcessor extends LogAnalyser {
    private Pattern startPattern;
    private Pattern endPattern;
    private List<String> errorLines = new ArrayList<String>();


    public BaseHandlerStatProcessor(Pattern startPattern, Pattern endPattern) {
        this.startPattern = startPattern;
        this.endPattern = endPattern;
    }


    @Override
    protected List<String> processFileContent(List<String> inputLines) throws Exception {
        for (int i = 0, inputLinesSize = inputLines.size(); i < inputLinesSize; i++) {
            String inputLine = inputLines.get(i);
            Matcher startMatcher = startPattern.matcher(inputLine);
            if (startMatcher.find()) {
                if (i == inputLines.size() - 1) {
                    // dernière ligne du log
                    break;
                }
                String dateTime = extractDateTime(inputLine);
                StringBuilder builder = new StringBuilder();
                builder.append(dateTime);
                builder.append(COLUMN_SEPARATOR);
                Matcher endMatcher = endPattern.matcher(inputLines.get(i + 1));
                if (!endMatcher.find()) {
                    errorLines.add(dateTime);
                    continue;
                }
                builder.append(endMatcher.group(1));
                outputLines.add(builder.toString());
            }
        }

        for (String errorLine : errorLines) {
            outputLines.add("\n");
            outputLines.add("Lignes de début d'exécution dont la ligne suivante n'indique pas un temps de traitement");
            outputLines.add(errorLine);
        }
        return outputLines;
    }
}
