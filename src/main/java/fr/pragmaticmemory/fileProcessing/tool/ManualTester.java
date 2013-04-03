package fr.pragmaticmemory.fileProcessing.tool;

import fr.pragmaticmemory.fileProcessing.core.Processor;
import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.DirectoryFileProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.ExtensionFileFiler;
import fr.pragmaticmemory.fileProcessing.fileProvider.FileProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.IncludeFiles;
import fr.pragmaticmemory.fileProcessing.fileProvider.SingleFileProvider;
import fr.pragmaticmemory.fileProcessing.processor.CopyProcessor;
import fr.pragmaticmemory.fileProcessing.processor.IncrementProcessor;
import fr.pragmaticmemory.fileProcessing.processor.RegExpProcessor;
import fr.pragmaticmemory.fileProcessing.routeProvider.DuplicateHierarchyRouteProvider;
import fr.pragmaticmemory.fileProcessing.routeProvider.IdentityFileRouteProvider;
import java.io.File;
public class ManualTester {

    public static void main(String[] args) throws Exception {
        test7();
    }


    private static void test1() throws Exception {

        FileProvider fileProvider = new SingleFileProvider(
              "C:\\dev\\projects\\gimw\\oscar-release-test\\src\\main\\usecase\\Isr\\GestionDesDroits.tokio");
        RouteProvider routeProvider = new IdentityFileRouteProvider(fileProvider);
        Processor processor = new IncrementProcessor(1, "\"\\d+\"");
        processor.process(routeProvider);
    }


    private static void test2() throws Exception {
        FileProvider fileProvider = new SingleFileProvider("C:\\Temp\\Test\\test.txt");
        RouteProvider routeProvider = new IdentityFileRouteProvider(fileProvider);
        Processor modifier = new RegExpProcessor("\\d", "chiffre");
        modifier.process(routeProvider);
    }


    private static void test4() throws Exception {
        FileProvider fileProvider = new SingleFileProvider("C:\\Temp\\Test\\test.txt");
        RouteProvider routeProvider = new IdentityFileRouteProvider(fileProvider);
        Processor modifier = new IncrementProcessor(-2);
        modifier.process(routeProvider);
    }


    private static void test5() throws Exception {
        DirectoryFileProvider fileProvider = new DirectoryFileProvider("C:\\Temp\\Test");
        RouteProvider routeProvider = new IdentityFileRouteProvider(fileProvider);
        Processor modifier = new IncrementProcessor(-2);
        modifier.process(routeProvider);
    }


    private static void test6() throws Exception {
        DirectoryFileProvider fileProvider = new DirectoryFileProvider("C:\\dev\\projects\\gimw\\oscar-release-test");
        FileProvider tokioFileProvider = new IncludeFiles(fileProvider, new ExtensionFileFiler(".tokio"));
        RouteProvider routeProvider = new IdentityFileRouteProvider(tokioFileProvider);
        Processor modifier = new IncrementProcessor(1, "\"\\d+\"");
        modifier.process(routeProvider);
    }


    private static void test7() throws Exception {
        DirectoryFileProvider fileProvider = new DirectoryFileProvider("C:\\dev\\projects\\gimw\\oscar-release-test");
        IncludeFiles tokioFileProvider = new IncludeFiles(fileProvider, new ExtensionFileFiler(".tokio"));
        RouteProvider tokioRouteProvider = new DuplicateHierarchyRouteProvider(tokioFileProvider,
                                                                               new File("C:\\Temp\\Save\\tokio"));
        Processor tokioProcessor = new CopyProcessor();
        tokioProcessor.process(tokioRouteProvider);

        IncludeFiles xmlFileProvider = new IncludeFiles(fileProvider, new ExtensionFileFiler(".xml"));
        RouteProvider xmlRouteProvider = new DuplicateHierarchyRouteProvider(xmlFileProvider,
                                                                             new File("C:\\Temp\\Save\\xml"));
        Processor xmlProcessor = new CopyProcessor();
        xmlProcessor.process(xmlRouteProvider);
    }
}
