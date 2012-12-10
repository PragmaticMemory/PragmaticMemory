package fr.pragmaticmemory.fileProcessing.tool;

import fr.pragmaticmemory.fileProcessing.core.Processor;
import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
import fr.pragmaticmemory.fileProcessing.processor.CopyProcessor;
import fr.pragmaticmemory.fileProcessing.processor.IncrementProcessor;
import fr.pragmaticmemory.fileProcessing.processor.RegExpProcessor;
import fr.pragmaticmemory.fileProcessing.routeProvider.DuplicateHierarchyRouteProvider;
import fr.pragmaticmemory.fileProcessing.routeProvider.IdentityFileRouteProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.DirectoryFileProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.ExtensionFileFiler;
import fr.pragmaticmemory.fileProcessing.fileProvider.FileProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.InclusiveFilter;
import fr.pragmaticmemory.fileProcessing.fileProvider.SingleFileProvider;
import java.io.File;
public class ManualTester {

    public static void main(String[] args) throws Exception {
        test7();
    }


    private static void test1() throws Exception {

        FileProvider fileProvider = new SingleFileProvider(
              "C:\\dev\\projects\\gimw\\oscar-release-test\\src\\main\\usecase\\Isr\\GestionDesDroits.tokio");
        RouteProvider routeProvider = new IdentityFileRouteProvider(fileProvider);
        Processor processor = new IncrementProcessor(routeProvider, 1, "\"\\d+\"");
        processor.process();
    }


    private static void test2() throws Exception {
        FileProvider fileProvider = new SingleFileProvider("C:\\Temp\\Test\\test.txt");
        RouteProvider routeProvider = new IdentityFileRouteProvider(fileProvider);
        Processor modifier = new RegExpProcessor(routeProvider, "\\d", "chiffre");
        modifier.process();
    }


    private static void test4() throws Exception {
        FileProvider fileProvider = new SingleFileProvider("C:\\Temp\\Test\\test.txt");
        RouteProvider routeProvider = new IdentityFileRouteProvider(fileProvider);
        Processor modifier = new IncrementProcessor(routeProvider, -2);
        modifier.process();
    }


    private static void test5() throws Exception {
        DirectoryFileProvider fileProvider = new DirectoryFileProvider("C:\\Temp\\Test");
        RouteProvider routeProvider = new IdentityFileRouteProvider(fileProvider);
        Processor modifier = new IncrementProcessor(routeProvider, -2);
        modifier.process();
    }


    private static void test6() throws Exception {
        DirectoryFileProvider fileProvider = new DirectoryFileProvider("C:\\dev\\projects\\gimw\\oscar-release-test");
        RouteProvider routeProvider = new IdentityFileRouteProvider(new InclusiveFilter(fileProvider, new ExtensionFileFiler(".tokio")));
        Processor modifier = new IncrementProcessor(routeProvider, 1, "\"\\d+\"");
        modifier.process();
    }

    private static void test7() throws Exception {
        RouteProvider routeProvider = new DuplicateHierarchyRouteProvider(new File("C:\\dev\\projects\\gimw\\oscar-release-test"), new File("C:\\Temp\\Save"));
        Processor modifier = new CopyProcessor(routeProvider);
        modifier.process();
    }
}
