package fr.pragmaticmemory.fileProcessing.fileModifier;
import fr.pragmaticmemory.fileProcessing.fileProvider.StringRouteProvider;
import junit.framework.Assert;
import junit.framework.TestCase;

public class RegExpModifierTest extends TestCase {

    public void testReplaceRegExp() throws Exception {
        String line = "Phrase contenant le nombre 345.";
        StringRouteProvider routeProvider = new StringRouteProvider(line);
        Modifier modifier = new RegExpModifier(routeProvider, "\\d", "chiffre");
        modifier.process();
        final String outputString = routeProvider.getOutputString();
        Assert.assertEquals("Phrase contenant le nombre chiffrechiffrechiffre.", outputString);
    }


    public void testReplaceRegExpWithCapturedGroup() throws Exception {
        String line = "Phrase contenant le nombre 345.";
        StringRouteProvider routeProvider = new StringRouteProvider(line);
        Modifier modifier = new RegExpModifier(routeProvider, "le nombre (\\d+)\\.", "ce cher nombre $1 !");
        modifier.process();
        final String outputString = routeProvider.getOutputString();
        Assert.assertEquals("Phrase contenant ce cher nombre 345 !", outputString);
    }
}