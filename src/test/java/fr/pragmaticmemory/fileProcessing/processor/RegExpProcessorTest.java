package fr.pragmaticmemory.fileProcessing.processor;
import fr.pragmaticmemory.fileProcessing.core.Processor;
import fr.pragmaticmemory.fileProcessing.routeProvider.StringRouteProvider;
import junit.framework.Assert;
import junit.framework.TestCase;

public class RegExpProcessorTest extends TestCase {

    public void testReplaceRegExp() throws Exception {
        String line = "Phrase contenant le nombre 345.";
        StringRouteProvider routeProvider = new StringRouteProvider(line);
        Processor processor = new RegExpProcessor(routeProvider, "\\d", "chiffre");
        processor.process();
        final String outputString = routeProvider.getOutputString();
        Assert.assertEquals("Phrase contenant le nombre chiffrechiffrechiffre.", outputString);
    }


    public void testReplaceRegExpWithCapturedGroup() throws Exception {
        String line = "Phrase contenant le nombre 345.";
        StringRouteProvider routeProvider = new StringRouteProvider(line);
        Processor processor = new RegExpProcessor(routeProvider, "le nombre (\\d+)\\.", "ce cher nombre $1 !");
        processor.process();
        final String outputString = routeProvider.getOutputString();
        Assert.assertEquals("Phrase contenant ce cher nombre 345 !", outputString);
    }
}