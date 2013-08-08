package fr.pragmaticmemory.fileProcessing.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
    Input : log serveur
 */
public class AuthentificationStat {

    public static void main(String[] args) throws Exception {
        LogAnalyserEngine logAnalyserEngine = new LogAnalyserEngine(false, new AuthStatProcessor());
        logAnalyserEngine.analyse();
    }


    private static String getUser() {
//        return "USER_BATCH";
//        return "cignett";
        return "\\w+";
    }


    private static class AuthStatProcessor extends LogAnalyser {

        private List<String> startLines = new ArrayList<String>();
        private List<String> endLines = new ArrayList<String>();
        private List<Boolean> isLoginAccepted = new ArrayList<Boolean>();
        //        static private final Pattern START_PATTERN = Pattern.compile("Adding node <country_of_" + USER + "_\\w+> to the platform");
        static private final Pattern START_PATTERN = Pattern.compile(
              "AbstractSecurityServiceHelper  - Trying to login with \\(user: " + getUser()
              + ", securityLevel: \\w+\\)");
        static private final Pattern LOGIN_ACCEPTED_PATTERN = Pattern.compile("Login '" + getUser() + "' accepted");
        static private final Pattern LOGIN_REFUSED_PATTERN = Pattern.compile("Login '" + getUser() + "' refused");


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
            extractDateTime(startLines);
            extractDateTime(endLines);

            for (int i = 0, startLinesSize = endLines.size(); i < startLinesSize; i++) {
                StringBuilder builder = new StringBuilder();
                if (isLoginAccepted.get(i)) {
                    builder.append(startLines.get(i));
                    builder.append(COLUMN_SEPARATOR);
                    builder.append(endLines.get(i));
                    outputLines.add(builder.toString());
                }
            }
        }
    }
}
