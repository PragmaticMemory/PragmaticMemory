package fr.pragmaticmemory.fileProcessing.tool;

import fr.pragmaticmemory.fileProcessing.core.Processor;
import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.SingleFileProvider;
import fr.pragmaticmemory.fileProcessing.routeProvider.MappedFileRouteProvider;
public class LogAnalyserEngine {

    static final String CLIENT_LOG_FILE_NAME = "C:\\Users\\cignett\\Desktop\\Nouveau dossier\\ClientLog.txt";
    static final String SERVER_LOG_FILE_NAME = "C:\\Users\\cignett\\Desktop\\Nouveau dossier\\ServeurLog.txt";
    static final String OUTPUT_FILE_NAME = "C:\\Users\\cignett\\Desktop\\Stat.txt";
    private Processor processor;
    private boolean useClientLog;


    public LogAnalyserEngine(boolean useClientLog, Processor processor) {
        this.processor = processor;
        this.useClientLog = useClientLog;
    }


    void analyse() throws Exception {
        SingleFileProvider inputFileProvider = new SingleFileProvider(useClientLog ?
                                                                      CLIENT_LOG_FILE_NAME :
                                                                      SERVER_LOG_FILE_NAME);
        SingleFileProvider outputFileProvider = new SingleFileProvider(OUTPUT_FILE_NAME);
        RouteProvider routeProvider = new MappedFileRouteProvider(inputFileProvider, outputFileProvider);
        processor.process(routeProvider);
    }
}
