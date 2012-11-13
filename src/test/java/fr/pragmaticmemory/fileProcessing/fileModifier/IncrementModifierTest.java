package fr.pragmaticmemory.fileProcessing.fileModifier;
import fr.pragmaticmemory.fileProcessing.fileProvider.StringRouteProvider;
import junit.framework.Assert;
import junit.framework.TestCase;

public class IncrementModifierTest extends TestCase {

    public void testIncrement() throws Exception {
        String line = "23 et 34";
        StringRouteProvider routeProvider = new StringRouteProvider(line);
        IncrementModifier modifier = new IncrementModifier(routeProvider);
        modifier.process();
        Assert.assertEquals("24 et 35", routeProvider.getOutputString());
    }


    public void testIncrementWithPositiveStep() throws Exception {
        String line = "99 et 56";
        StringRouteProvider routeProvider = new StringRouteProvider(line);
        IncrementModifier modifier = new IncrementModifier(routeProvider, 3);
        modifier.process();
        Assert.assertEquals("102 et 59", routeProvider.getOutputString());
    }


    public void testIncrementWithNegativeStep() throws Exception {
        String line = "1 et 22";
        StringRouteProvider routeProvider = new StringRouteProvider(line);
        IncrementModifier modifier = new IncrementModifier(routeProvider, -4);
        modifier.process();
        Assert.assertEquals("-3 et 18", routeProvider.getOutputString());
    }


    public void testLimitedIncrement() throws Exception {
        String line = "<tag1 attribute1=\"34\" attribute2=\"123\">";
        StringRouteProvider routeProvider = new StringRouteProvider(line);
        IncrementModifier modifier = new IncrementModifier(routeProvider, 1, "\"\\d+\"");
        modifier.process();
        Assert.assertEquals("<tag1 attribute1=\"35\" attribute2=\"124\">", routeProvider.getOutputString());
    }


    public void testBigIntegerIncrement() throws Exception {
        String line = "Entier énorme : 111111111111.";
        StringRouteProvider routeProvider = new StringRouteProvider(line);
        IncrementModifier modifier = new IncrementModifier(routeProvider);
        modifier.process();
        Assert.assertEquals("Entier énorme : 111111111112.", routeProvider.getOutputString());
    }
}
