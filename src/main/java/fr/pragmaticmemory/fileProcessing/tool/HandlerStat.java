package fr.pragmaticmemory.fileProcessing.tool;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * input : log serveur
 */
public class HandlerStat {

    public static void main(String[] args) throws Exception {
        LogAnalyserEngine logAnalyserEngine = new LogAnalyserEngine(false, new HandlerStatProcessor());
        logAnalyserEngine.analyse();
    }


    private static String getHandler() {
        return "selectHoldDate";
    }


    private static class HandlerStatProcessor extends LogAnalyser {
        static private final Pattern START_PATTERN = Pattern.compile("Execution du handler " + getHandler());
        static private final Pattern END_PATTERN = Pattern.compile("Traitement de la requete en (\\d+) ms");


        @Override
        protected List<String> processFileContent(List<String> inputLines) throws Exception {
            for (int i = 0, inputLinesSize = inputLines.size(); i < inputLinesSize; i++) {
                String inputLine = inputLines.get(i);
                Matcher startMatcher = START_PATTERN.matcher(inputLine);
                if (startMatcher.find()) {
                    if (i == inputLines.size() - 1) {
                        // dernière ligne du log
                        break;
                    }
                    StringBuilder builder = new StringBuilder();
                    builder.append(extractDateTime(inputLine));
                    builder.append(COLUMN_SEPARATOR);
                    Matcher endMatcher = END_PATTERN.matcher(inputLines.get(i + 1));
                    if (!endMatcher.find()) {
                        builder.append("ligne suivant l'exécution du handler n'indiquant pas un temps de traitement");
                        continue;
                    }
                    builder.append(endMatcher.group(1));
                    outputLines.add(builder.toString());
                }
            }
            return outputLines;
        }
    }
}
