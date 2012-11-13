package fr.pragmaticmemory.fileProcessing.fileModifier;
import fr.pragmaticmemory.fileProcessing.fileProvider.StringRouteProvider;
import junit.framework.Assert;
import junit.framework.TestCase;

public class TextReplacementModifierTest extends TestCase {

    public void testReplacement() throws Exception {
        String line = "Phrase contenant un texte à remplacer par un autre.";
        StringRouteProvider routeProvider = new StringRouteProvider(line);
        Modifier modifier = new TextReplacementModifier(routeProvider, "texte à remplacer", "un autre");
        modifier.process();
        final String outputString = routeProvider.getOutputString();
        Assert.assertEquals("Phrase contenant un un autre par un autre.", outputString);
    }
}
