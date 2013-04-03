package fr.pragmaticmemory.fileProcessing.processor;
import fr.pragmaticmemory.fileProcessing.core.Processor;
import fr.pragmaticmemory.fileProcessing.routeProvider.StringRouteProvider;
import junit.framework.Assert;
import junit.framework.TestCase;

public class TextReplacementProcessorTest extends TestCase {

    public void testReplacement() throws Exception {
        String line = "Phrase contenant un texte à remplacer par un autre.";
        StringRouteProvider routeProvider = new StringRouteProvider(line);
        Processor processor = new TextReplacementProcessor("texte à remplacer", "un autre");
        processor.process(routeProvider);
        final String outputString = routeProvider.getOutputString();
        Assert.assertEquals("Phrase contenant un un autre par un autre.", outputString);
    }
}
