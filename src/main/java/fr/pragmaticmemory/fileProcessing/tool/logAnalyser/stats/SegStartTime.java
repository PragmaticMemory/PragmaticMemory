package fr.pragmaticmemory.fileProcessing.tool.logAnalyser.stats;

import fr.pragmaticmemory.fileProcessing.tool.logAnalyser.core.LogAnalyser;
import fr.pragmaticmemory.fileProcessing.tool.logAnalyser.core.LogAnalyserEngine;
import fr.pragmaticmemory.fileProcessing.tool.logAnalyser.core.SideEnum;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class SegStartTime {
    public static void main(String[] args) throws Exception {
        LogAnalyserEngine logAnalyserEngine = new LogAnalyserEngine(SideEnum.CLIENT, new SegStartTimeProcessor());
        logAnalyserEngine.analyse();
    }


    private static class SegStartTimeProcessor extends LogAnalyser {
        static private final Pattern START_PATTERN = Pattern.compile(
              "Plugin\\.start net\\.codjo\\.segmentation\\.batch\\.plugin\\.SegmentationBatchPlugin");
        String day = "";


        @Override
        protected List<String> processFileContent(List<String> inputLines) throws Exception {
            for (int i = 0, inputLinesSize = inputLines.size(); i < inputLinesSize; i++) {
                String inputLine = inputLines.get(i);
                Matcher startMatcher = START_PATTERN.matcher(inputLine);
                if (startMatcher.find()) {
                    String newDay = extractDate(inputLine);
                    if (!newDay.equals(day)) {
                        String date = extractDate(inputLine);
                        String time = extractTime(inputLine);
                        StringBuilder builder = new StringBuilder();
                        builder.append(date);
                        builder.append(COLUMN_SEPARATOR);
                        builder.append(time);
                        outputLines.add(builder.toString());
                        day = newDay;
                    }
                }
            }

            return outputLines;
        }
    }
}
