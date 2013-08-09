package fr.pragmaticmemory.fileProcessing.tool.logAnalyser.stats;
import fr.pragmaticmemory.fileProcessing.tool.logAnalyser.core.LogAnalyserEngine;
import fr.pragmaticmemory.fileProcessing.tool.logAnalyser.core.SideEnum;
import java.util.regex.Pattern;

public class ServerHandlerStat {

    final public static String HANDLER = "selectHoldDate";
//        final public static String HANDLER = "selectAxeList";


    public static void main(String[] args) throws Exception {
        LogAnalyserEngine logAnalyserEngine = new LogAnalyserEngine(SideEnum.SERVER, new ServerHandlerStatProcessor());
        logAnalyserEngine.analyse();
    }


    private static class ServerHandlerStatProcessor extends BaseHandlerStatProcessor {
        static private final Pattern START_PATTERN = Pattern.compile("Execution du handler " + HANDLER);
        static private final Pattern END_PATTERN = Pattern.compile("Traitement de la requete en (\\d+) ms");


        private ServerHandlerStatProcessor() {
            super(START_PATTERN, END_PATTERN);
        }
    }
}
