package fr.pragmaticmemory.fileProcessing.fileModifier;
import fr.pragmaticmemory.fileProcessing.fileProvider.SingleStringRouteProvider;
import junit.framework.TestCase;

public class IncrementModifierTest extends TestCase {

    public void testIncrement() throws Exception {
        String line = "23 et 34";
        SingleStringRouteProvider transfertProvider = new SingleStringRouteProvider(line);
        IncrementModifier modifier = new IncrementModifier(transfertProvider);
        modifier.process();
        final String resultString = transfertProvider.getResultString();
        System.out.println("resultString = " + resultString);
    }
}
