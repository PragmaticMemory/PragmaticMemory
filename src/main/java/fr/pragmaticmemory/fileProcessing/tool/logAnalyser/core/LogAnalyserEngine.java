package fr.pragmaticmemory.fileProcessing.tool.logAnalyser.core;

import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.SingleFileProvider;
import fr.pragmaticmemory.fileProcessing.routeProvider.MappedFileRouteProvider;
public class LogAnalyserEngine {

    static final String CLIENT_LOG_FILE_NAME = "C:\\Users\\cignett\\Desktop\\Nouveau dossier\\ClientLog.txt";
    static final String SERVER_LOG_FILE_NAME = "C:\\Users\\cignett\\Desktop\\Nouveau dossier\\ServeurLog.txt";
    static final String OUTPUT_FILE_NAME = "C:\\Users\\cignett\\Desktop\\Stat.txt";
    private SideEnum sideEnum;
    private LogAnalyser logAnalyser;


    public LogAnalyserEngine(SideEnum sideEnum, LogAnalyser logAnalyser) {
        this.sideEnum = sideEnum;
        this.logAnalyser = logAnalyser;
    }


    public void analyse() throws Exception {
        SingleFileProvider inputFileProvider = new SingleFileProvider(sideEnum == SideEnum.CLIENT ?
                                                                      CLIENT_LOG_FILE_NAME :
                                                                      SERVER_LOG_FILE_NAME);
        SingleFileProvider outputFileProvider = new SingleFileProvider(OUTPUT_FILE_NAME);
        RouteProvider routeProvider = new MappedFileRouteProvider(inputFileProvider, outputFileProvider);
        logAnalyser.process(routeProvider);
    }
}
