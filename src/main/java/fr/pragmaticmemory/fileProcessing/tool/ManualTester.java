package fr.pragmaticmemory.fileProcessing.tool;

import fr.pragmaticmemory.fileProcessing.fileModifier.FileModifier;
import fr.pragmaticmemory.fileProcessing.fileModifier.TextReplacementFileModifier;
import fr.pragmaticmemory.fileProcessing.fileProvider.FileProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.SingleFileProvider;
public class ManualTester {

    public static void main(String[] args) throws Exception {

        FileProvider provider = new SingleFileProvider("C:\\Temp\\test.txt");
        FileModifier modifier = new TextReplacementFileModifier(provider,
                                                                "gptaux-release-test-2-2",
                                                                "gptaux-release-test");
        modifier.process();
    }
}
