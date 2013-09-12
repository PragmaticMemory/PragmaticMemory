package fr.pragmaticmemory.fileProcessing.tool.logAnalyser.stats;

import fr.pragmaticmemory.fileProcessing.tool.logAnalyser.core.LogAnalyserEngine;
import fr.pragmaticmemory.fileProcessing.tool.logAnalyser.core.SideEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class SuccessfullSegStats {

    private SuccessfullSegStats() {
    }


    public static void main(String[] args) throws Exception {
        LogAnalyserEngine logAnalyserEngine = new LogAnalyserEngine(SideEnum.CLIENT, new SegStatProcessor());
        logAnalyserEngine.analyse();
    }


    private static class SegStatProcessor extends BaseHandlerStatProcessor {

        static private final Pattern START_PATTERN = Pattern.compile(
              "Plugin\\.start net\\.codjo\\.segmentation\\.batch\\.plugin\\.SegmentationBatchPlugin");
        static private final Pattern END_PATTERN = Pattern.compile("Fin du traitement !");
        String day = "";
        List<String> startList = new ArrayList<String>();
        List<String> endList = new ArrayList<String>();


        private SegStatProcessor() {
            super(START_PATTERN, END_PATTERN);
        }


        @Override
        protected List<String> processFileContent(List<String> inputLines) throws Exception {
            for (int i = 0, inputLinesSize = inputLines.size(); i < inputLinesSize; i++) {
                String inputLine = inputLines.get(i);

                Matcher startMatcher = START_PATTERN.matcher(inputLine);
                Matcher endMatcher = END_PATTERN.matcher(inputLine);
                boolean isStart = startMatcher.find();
                boolean isEnd = endMatcher.find();

                if (isStart || isEnd) {
                    String newDay = extractDay(inputLine);
                    if (!newDay.equals(day)) {
                        addOutputLine();
                        day = newDay;
                        startList.clear();
                        endList.clear();
                    }
                }
                if (isStart) {
                    String startTime = extractDateTime(inputLine);
                    startList.add(startTime);
                }
                if (isEnd) {
                    String endTime = extractDateTime(inputLine);
                    endList.add(endTime);
                }
            }

            return outputLines;
        }


        private void addOutputLine() throws Exception {
            if (startList.isEmpty()) {
                return;
            }
            if (endList.isEmpty()) {
                return;
            }
            if (endList.size() > 1) {
                throw new Exception("Plus de 2 fins de segmentation le " + day);
            }
            StringBuilder builder = new StringBuilder();
            builder.append(startList.get(startList.size() - 1));
            builder.append(COLUMN_SEPARATOR);
            builder.append(endList.get(0));
            outputLines.add(builder.toString());
        }
    }
}
