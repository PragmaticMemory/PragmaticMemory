package fr.pragmaticmemory.fileProcessing.tool;

import fr.pragmaticmemory.fileProcessing.core.Processor;
import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.SingleFileProvider;
import fr.pragmaticmemory.fileProcessing.routeProvider.MappedFileRouteProvider;
public class FileTool<T extends Processor> {

    private String inputFileName;
    private String outputFileName;
    private T processor;


    public FileTool(String inputFileName, String outputFileName, T processor) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        this.processor = processor;
    }

    protected void process() throws Exception {
        SingleFileProvider inputFileProvider = new SingleFileProvider(inputFileName);
        SingleFileProvider outputFileProvider = new SingleFileProvider(outputFileName);
        RouteProvider routeProvider = new MappedFileRouteProvider(inputFileProvider, outputFileProvider);
        processor.process(routeProvider);
    }
}
