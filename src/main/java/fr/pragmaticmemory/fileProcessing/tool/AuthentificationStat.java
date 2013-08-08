package fr.pragmaticmemory.fileProcessing.tool;

import fr.pragmaticmemory.fileProcessing.core.Processor;
import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.SingleFileProvider;
import fr.pragmaticmemory.fileProcessing.processor.TextProcessor;
import fr.pragmaticmemory.fileProcessing.routeProvider.MappedFileRouteProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
    Input : log serveur
 */
public class AuthentificationStat {

//            private static final String USER = "USER_BATCH";
    private static final String USER = "cignett";
//    private static final String USER = "\\w+";


    public static void main(String[] args) throws Exception {
        String inputFileName = null;
        String outputFileName = null;
        if (args.length == 0) {
            inputFileName = "C:\\Users\\cignett\\Desktop\\Log.txt";
            outputFileName = "C:\\Users\\cignett\\Desktop\\Stat.txt";
        }

        SingleFileProvider inputFileProvider = new SingleFileProvider(inputFileName);
        SingleFileProvider outputFileProvider = new SingleFileProvider(outputFileName);
        RouteProvider routeProvider = new MappedFileRouteProvider(inputFileProvider, outputFileProvider);
        Processor processor = new AuthStatProcessor();
        processor.process(routeProvider);
    }


    private static class AuthStatProcessor extends TextProcessor {

        private List<String> startLines = new ArrayList<String>();
        private List<String> endLines = new ArrayList<String>();
        private List<Boolean> isLoginAccepted = new ArrayList<Boolean>();
        private List<String> outputLines = new ArrayList<String>();
        //                static private final Pattern START_PATTERN = Pattern.compile("Adding node <country_of_" + USER + "_\\w+> to the platform");
        static private final Pattern START_PATTERN = Pattern.compile(
              "AbstractSecurityServiceHelper  - Trying to login with \\(user: " + USER + ", securityLevel: \\w+\\)");

        static private final Pattern LOGIN_ACCEPTED_PATTERN = Pattern.compile("Login '" + USER + "' accepted");
        static private final Pattern LOGIN_REFUSED_PATTERN = Pattern.compile("Login '" + USER + "' refused");
        static private final Pattern DATE_TIME_PATTERN = Pattern.compile("(\\s*\\d+ \\w+ \\d+ \\d+:\\d+:\\d+,\\d+).+");


        @Override
        protected List<String> processFileContent(List<String> inputLines) throws Exception {
            for (String inputLine : inputLines) {
                Matcher startMatcher = START_PATTERN.matcher(inputLine);
                if (startMatcher.find()) {
                    startLines.add(inputLine);
                    continue;
                }
                Matcher loginAcceptedMatcher = LOGIN_ACCEPTED_PATTERN.matcher(inputLine);
                if (loginAcceptedMatcher.find()) {
                    endLines.add(inputLine);
                    isLoginAccepted.add(true);
                }
                Matcher loginRefusedMatcher = LOGIN_REFUSED_PATTERN.matcher(inputLine);
                if (loginRefusedMatcher.find()) {
                    endLines.add(inputLine);
                    isLoginAccepted.add(false);
                }
            }

            generateOutputLines();
            return outputLines;
        }


        private void generateOutputLines() {
            clean(startLines);
            clean(endLines);

            for (int i = 0, startLinesSize = endLines.size(); i < startLinesSize; i++) {
                StringBuilder builder = new StringBuilder();
                if (isLoginAccepted.get(i)) {
                    builder.append(startLines.get(i));
                    builder.append("|");
                    builder.append(endLines.get(i));
                    outputLines.add(builder.toString());
                }
            }
        }


        private void clean(List<String> lines) {
            for (int i = 0, linesSize = lines.size(); i < linesSize; i++) {
                String line = lines.get(i);
                Matcher cleanMatcher = DATE_TIME_PATTERN.matcher(line);
                lines.set(i, cleanMatcher.replaceAll("$1"));
            }
        }
    }
}
