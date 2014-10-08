package fr.pragmaticmemory.fileProcessing.tool;
import fr.pragmaticmemory.fileProcessing.core.Processor;
import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.SingleFileProvider;
import fr.pragmaticmemory.fileProcessing.processor.arrayFormatter.SubversionLogProcessor;
import fr.pragmaticmemory.fileProcessing.routeProvider.MappedFileRouteProvider;

public class SubversionLogFormatter {

    private SubversionLogFormatter() {
    }


    public static void main(String[] args) throws Exception {
        String inputFileName = "C:\\temp\\input.txt";
        String outputFileName = "C:\\temp\\output.txt";
        SingleFileProvider inputFileProvider = new SingleFileProvider(inputFileName);
        SingleFileProvider outputFileProvider = new SingleFileProvider(outputFileName);
        RouteProvider routeProvider = new MappedFileRouteProvider(inputFileProvider, outputFileProvider);
        Processor processor = new SubversionLogProcessor();
        processor.process(routeProvider);
    }
}
