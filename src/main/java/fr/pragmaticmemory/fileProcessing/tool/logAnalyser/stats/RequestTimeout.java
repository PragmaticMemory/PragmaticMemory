package fr.pragmaticmemory.fileProcessing.tool.logAnalyser.stats;
import fr.pragmaticmemory.fileProcessing.tool.logAnalyser.core.LogAnalyser;
import fr.pragmaticmemory.fileProcessing.tool.logAnalyser.core.LogAnalyserEngine;
import fr.pragmaticmemory.fileProcessing.tool.logAnalyser.core.SideEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Liste les handler dont l'ex�cution d�passe la minute � partir des logs serveurs
 */
public class RequestTimeout {
    static final Pattern TIMEOUT_PATTERN = Pattern.compile(
          "(\\d{2}\\s\\w{3}\\s\\d{4}\\s\\d{2}:\\d{2}:\\d{2},\\d{3}).*Traitement de la requete en (\\d+)\\s+ms");
    static final Pattern HANDLER_PATTERN = Pattern.compile("Execution du handler (\\w+)");


    public static void main(String[] args) throws Exception {
        LogAnalyserEngine logAnalyserEngine = new LogAnalyserEngine(SideEnum.SERVER, new RequestTimeoutProcessor());
        logAnalyserEngine.analyse();
    }


    static private class RequestTimeoutProcessor extends LogAnalyser {

        @Override
        protected List<String> processFileContent(List<String> inputLines) throws Exception {
            List<String> output = new ArrayList<String>();
            String handler = "";
            for (int lineIndex = 1, inputLinesSize = inputLines.size(); lineIndex < inputLinesSize; lineIndex++) {
                String line = inputLines.get(lineIndex);
                Matcher handlerMatcher = HANDLER_PATTERN.matcher(line);
                if (handlerMatcher.find()) {
                    handler = handlerMatcher.group(1);
                }
                Matcher timeoutMatcher = TIMEOUT_PATTERN.matcher(line);
                if (timeoutMatcher.find()) {
                    StringBuilder builder = new StringBuilder();
                    String time = timeoutMatcher.group(1);
                    int duration = Integer.parseInt(timeoutMatcher.group(2));
                    if (duration > 60000) {
                        builder.append(time);
                        builder.append("|");
                        builder.append(handler);
                        builder.append("|");
                        builder.append(duration);
                        output.add(builder.toString());
                    }
                }
            }
            return output;
        }
    }
}
