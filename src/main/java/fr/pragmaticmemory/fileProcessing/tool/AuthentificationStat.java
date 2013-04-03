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
public class AuthentificationStat {

    private static final String USER = "USER_BATCH";
//        private static final String USER="\\w+";


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
        Processor processor = new AuthStatProcessor(routeProvider);
        processor.process();
    }


    private static class AuthStatProcessor extends TextProcessor {

        private List<String> startLines = new ArrayList<String>();
        private List<String> endLines = new ArrayList<String>();
        private List<String> outputLines = new ArrayList<String>();
        static private final Pattern START_PATTERN = Pattern.compile(
              "Adding node <country_of_" + USER + "_\\w+> to the platform");
        static private final Pattern END_PATTERN = Pattern.compile("Login '" + USER + "' accepted");
        static private final Pattern CLEAN_PATTERN = Pattern.compile("(\\s*\\d+ \\w+ \\d+ \\d+:\\d+:\\d+,\\d+).+");


        private AuthStatProcessor(RouteProvider routeProvider) {
            super(routeProvider);
        }


        @Override
        protected List<String> processFileContent(List<String> inputLines) throws Exception {
            for (String inputLine : inputLines) {
                Matcher startMatcher = START_PATTERN.matcher(inputLine);
                if (startMatcher.find()) {
                    startLines.add(inputLine);

                    continue;
                }
                Matcher endMatcher = END_PATTERN.matcher(inputLine);
                if (endMatcher.find()) {
                    endLines.add(inputLine);
                }
            }

            outputLines.add("Start :");
            for (String startLine : startLines) {
                outputLines.add(startLine);
            }
            outputLines.add("End :");
            for (String endLine : endLines) {
                outputLines.add(endLine);
            }

            clean(outputLines);
            return outputLines;
        }


        private void clean(List<String> lines) {
            for (int i = 0, linesSize = lines.size(); i < linesSize; i++) {
                String line = lines.get(i);
                Matcher cleanMatcher = CLEAN_PATTERN.matcher(line);
                lines.set(i, cleanMatcher.replaceAll("$1"));
            }
        }
    }
}
