package fr.pragmaticmemory.fileProcessing.processor;
import fr.pragmaticmemory.fileProcessing.routeProvider.StringRouteProvider;
import junit.framework.Assert;
import junit.framework.TestCase;

public class IncrementProcessorTest extends TestCase {

    public void testIncrement() throws Exception {
        String line = "23 et 34";
        StringRouteProvider routeProvider = new StringRouteProvider(line);
        IncrementProcessor modifier = new IncrementProcessor();
        modifier.process(routeProvider);
        Assert.assertEquals("24 et 35", routeProvider.getOutputString());
    }


    public void testIncrementWithPositiveStep() throws Exception {
        String line = "99 et 56";
        StringRouteProvider routeProvider = new StringRouteProvider(line);
        IncrementProcessor modifier = new IncrementProcessor(3);
        modifier.process(routeProvider);
        Assert.assertEquals("102 et 59", routeProvider.getOutputString());
    }


    public void testIncrementWithNegativeStep() throws Exception {
        String line = "1 et 22";
        StringRouteProvider routeProvider = new StringRouteProvider(line);
        IncrementProcessor modifier = new IncrementProcessor(-4);
        modifier.process(routeProvider);
        Assert.assertEquals("-3 et 18", routeProvider.getOutputString());
    }


    public void testLimitedIncrement() throws Exception {
        String line = "<tag1 attribute1=\"34\" attribute2=\"123\">";
        StringRouteProvider routeProvider = new StringRouteProvider(line);
        IncrementProcessor modifier = new IncrementProcessor(1, "\"\\d+\"");
        modifier.process(routeProvider);
        Assert.assertEquals("<tag1 attribute1=\"35\" attribute2=\"124\">", routeProvider.getOutputString());
    }


    public void testBigIntegerIncrement() throws Exception {
        String line = "Entier énorme : 111111111111.";
        StringRouteProvider routeProvider = new StringRouteProvider(line);
        IncrementProcessor modifier = new IncrementProcessor();
        modifier.process(routeProvider);
        Assert.assertEquals("Entier énorme : 111111111112.", routeProvider.getOutputString());
    }
}
