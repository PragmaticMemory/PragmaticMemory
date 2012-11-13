package fr.pragmaticmemory.fileProcessing.fileModifier;
import fr.pragmaticmemory.fileProcessing.fileProvider.StringRouteProvider;
import junit.framework.TestCase;

public class IncrementModifierTest extends TestCase {

    public void testIncrement() throws Exception {
        String line = "23 et 34";
        StringRouteProvider routeProvider = new StringRouteProvider(line);
        IncrementModifier modifier = new IncrementModifier(routeProvider);
        modifier.process();
        final String outputString = routeProvider.getOutputString();
        System.out.println("outputString = " + outputString);
    }
}
