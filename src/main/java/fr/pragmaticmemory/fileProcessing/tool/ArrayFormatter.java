package fr.pragmaticmemory.fileProcessing.tool;

import fr.pragmaticmemory.fileProcessing.core.Processor;
import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.SingleFileProvider;
import fr.pragmaticmemory.fileProcessing.processor.ArrayFormatterProcessor;
import fr.pragmaticmemory.fileProcessing.routeProvider.MappedFileRouteProvider;
public class ArrayFormatter {

    public static void main(String[] args) throws Exception {
        String inputFileName = null;
        String outputFileName = null;
        if (args.length == 0) {
            inputFileName = "C:\\dev\\projects\\PragmaticMemory\\unformattedArray.txt";
            outputFileName = "C:\\dev\\projects\\PragmaticMemory\\formattedArray.txt";
        }
        else if (args.length < 2) {
            String helpMessage = "usage : \n"
                                 + "premier argument : chemin du fichier d'entree (tableau non formatte)\n"
                                 + "second argument : chemin du fichier de sortie (tableau formatte)";
            System.out.println(helpMessage);
        }
        else {
            inputFileName = args[0];
            outputFileName = args[1];
        }

        SingleFileProvider inputFileProvider = new SingleFileProvider(inputFileName);
        SingleFileProvider outputFileProvider = new SingleFileProvider(outputFileName);
        RouteProvider routeProvider = new MappedFileRouteProvider(inputFileProvider, outputFileProvider);
        Processor processor = new ArrayFormatterProcessor();
        processor.process(routeProvider);
    }
}
