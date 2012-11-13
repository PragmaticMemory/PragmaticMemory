package fr.pragmaticmemory.fileProcessing.tool;

import fr.pragmaticmemory.fileProcessing.fileModifier.IncrementModifier;
import fr.pragmaticmemory.fileProcessing.fileModifier.Modifier;
import fr.pragmaticmemory.fileProcessing.fileModifier.RegExpModifier;
import fr.pragmaticmemory.fileProcessing.fileModifier.TextReplacementModifier;
import fr.pragmaticmemory.fileProcessing.fileProvider.DirectoryFileProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.FileProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.IdentityFileRouteProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.RouteProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.SingleFileProvider;
public class ManualTester {

    public static void main(String[] args) throws Exception {
//        test1();
        test2();
//        test3();
//        test5();
    }


    private static void test1() throws Exception {
        FileProvider fileProvider = new SingleFileProvider("C:\\Temp\\Test\\test.txt");
        RouteProvider routeProvider = new IdentityFileRouteProvider(fileProvider);
        Modifier modifier = new TextReplacementModifier(routeProvider, "main", "3");
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
        IncrementModifier modifier = new IncrementModifier(routeProvider);
        modifier.process();
    }


    private static void test5() throws Exception {
        DirectoryFileProvider fileProvider = new DirectoryFileProvider("C:\\Temp\\Test");
        RouteProvider routeProvider = new IdentityFileRouteProvider(fileProvider);
        IncrementModifier modifier = new IncrementModifier(routeProvider);
        modifier.process();
    }
}
