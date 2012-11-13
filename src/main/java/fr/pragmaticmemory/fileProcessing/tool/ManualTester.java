package fr.pragmaticmemory.fileProcessing.tool;

import fr.pragmaticmemory.fileProcessing.fileModifier.IncrementModifier;
import fr.pragmaticmemory.fileProcessing.fileModifier.Modifier;
import fr.pragmaticmemory.fileProcessing.fileModifier.RegExpModifier;
import fr.pragmaticmemory.fileProcessing.fileProvider.DirectoryFileProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.ExtensionFileFiler;
import fr.pragmaticmemory.fileProcessing.fileProvider.FileProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.IdentityFileRouteProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.InclusiveFilter;
import fr.pragmaticmemory.fileProcessing.fileProvider.RouteProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.SingleFileProvider;
public class ManualTester {

    public static void main(String[] args) throws Exception {
        test6();
    }


    private static void test1() throws Exception {

        FileProvider fileProvider = new SingleFileProvider(
              "C:\\dev\\projects\\gimw\\oscar-release-test\\src\\main\\usecase\\Isr\\GestionDesDroits.tokio");
        RouteProvider routeProvider = new IdentityFileRouteProvider(fileProvider);
        Modifier modifier = new IncrementModifier(routeProvider, 1, "\"\\d+\"");
        modifier.process();
    }


    private static void test2() throws Exception {
        FileProvider fileProvider = new SingleFileProvider("C:\\Temp\\Test\\test.txt");
        RouteProvider routeProvider = new IdentityFileRouteProvider(fileProvider);
        RegExpModifier modifier = new RegExpModifier(routeProvider, "\\d", "chiffre");
        modifier.process();
    }


    private static void test4() throws Exception {
        FileProvider fileProvider = new SingleFileProvider("C:\\Temp\\Test\\test.txt");
        RouteProvider routeProvider = new IdentityFileRouteProvider(fileProvider);
        IncrementModifier modifier = new IncrementModifier(routeProvider, -2);
        modifier.process();
    }


    private static void test5() throws Exception {
        DirectoryFileProvider fileProvider = new DirectoryFileProvider("C:\\Temp\\Test");
        RouteProvider routeProvider = new IdentityFileRouteProvider(fileProvider);
        IncrementModifier modifier = new IncrementModifier(routeProvider, -2);
        modifier.process();
    }


    private static void test6() throws Exception {
        DirectoryFileProvider fileProvider = new DirectoryFileProvider("C:\\dev\\projects\\gimw\\oscar-release-test");
        RouteProvider routeProvider = new IdentityFileRouteProvider(new InclusiveFilter(fileProvider, new ExtensionFileFiler(".tokio")));
        IncrementModifier modifier = new IncrementModifier(routeProvider, 1, "\"\\d+\"");
        modifier.process();
    }
}
