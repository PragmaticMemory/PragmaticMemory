package fr.pragmaticmemory.fileProcessing.tool;

import fr.pragmaticmemory.fileProcessing.fileModifier.FileModifier;
import fr.pragmaticmemory.fileProcessing.fileModifier.IncrementFileModifier;
import fr.pragmaticmemory.fileProcessing.fileModifier.IncrementModifier;
import fr.pragmaticmemory.fileProcessing.fileModifier.RegExpFileModifier;
import fr.pragmaticmemory.fileProcessing.fileModifier.TextReplacementFileModifier;
import fr.pragmaticmemory.fileProcessing.fileProvider.FileProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.SingleFileProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.IdentityFileRouteProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.RouteProvider;
public class ManualTester {

    public static void main(String[] args) throws Exception {

//        test1();
//        test2();
//        test3();
        test4();
    }


    private static void test1() throws Exception {
        FileProvider provider = new SingleFileProvider("C:\\Temp\\test.txt");
        FileModifier modifier = new TextReplacementFileModifier(provider, "main", "3");
        modifier.process();
    }


    private static void test2() throws Exception {
        FileProvider provider = new SingleFileProvider("C:\\Temp\\test.txt");
        FileModifier modifier = new RegExpFileModifier(provider, "\\d", "chiffre");
        modifier.process();
    }


    private static void test3() throws Exception {
        FileProvider provider = new SingleFileProvider("C:\\Temp\\test.txt");
        FileModifier modifier = new IncrementFileModifier(provider);
        modifier.process();
    }

    private static void test4() throws Exception {
        RouteProvider provider = new IdentityFileRouteProvider("C:\\Temp\\test.txt");
        IncrementModifier modifier = new IncrementModifier(provider);
        modifier.process();
    }
}
