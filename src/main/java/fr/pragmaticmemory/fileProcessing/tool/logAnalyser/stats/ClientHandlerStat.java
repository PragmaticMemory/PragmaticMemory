package fr.pragmaticmemory.fileProcessing.tool.logAnalyser.stats;
import fr.pragmaticmemory.fileProcessing.tool.logAnalyser.core.LogAnalyserEngine;
import fr.pragmaticmemory.fileProcessing.tool.logAnalyser.core.SideEnum;
import java.util.regex.Pattern;

public class ClientHandlerStat {

    final public static String HANDLER = "selectHoldDate";
//    final public static String HANDLER = "selectAxeList";


    public static void main(String[] args) throws Exception {
        LogAnalyserEngine logAnalyserEngine = new LogAnalyserEngine(SideEnum.CLIENT, new ClientHandlerStatProcessor());
        logAnalyserEngine.analyse();
    }


    private static class ClientHandlerStatProcessor extends BaseHandlerStatProcessor {
        static private final Pattern START_PATTERN = Pattern.compile(
              "Envoie de \\d+ requete\\(s\\) au serveur " + HANDLER + " avec un timeout de \\d+ ms");
        static private final Pattern END_PATTERN = Pattern.compile("Envoie de la requete au serveur \\((\\d+) ms\\)");


        private ClientHandlerStatProcessor() {
            super(START_PATTERN, END_PATTERN);
        }
    }
}
