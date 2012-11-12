package fr.pragmaticmemory.fileProcessing.tool;

import fr.pragmaticmemory.fileProcessing.fileModifier.FileModifier;
import fr.pragmaticmemory.fileProcessing.fileModifier.IncrementFileModifier;
import fr.pragmaticmemory.fileProcessing.fileProvider.FileProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.SingleFileProvider;
public class ManualTester {

    public static void main(String[] args) throws Exception {

//        FileProvider provider = new SingleFileProvider("C:\\Temp\\test.txt");
//        FileModifier modifier = new TextReplacementFileModifier(provider,
//                                                                "main",
//                                                                "3");
//        modifier.process();
//        FileProvider provider = new SingleFileProvider("C:\\Temp\\test.txt");
//        FileModifier modifier = new RegExpFileModifier(provider, "\\d", "chiffre");
//        modifier.process();
        FileProvider provider = new SingleFileProvider("C:\\Temp\\test.txt");
        FileModifier modifier = new IncrementFileModifier(provider);
        modifier.process();
    }
}
