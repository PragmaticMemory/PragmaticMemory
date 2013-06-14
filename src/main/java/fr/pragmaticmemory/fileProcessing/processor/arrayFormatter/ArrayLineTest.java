package fr.pragmaticmemory.fileProcessing.processor.arrayFormatter;
import fr.pragmaticmemory.fileProcessing.processor.arrayFormatter.ArrayLine;
import junit.framework.Assert;
import junit.framework.TestCase;

public class ArrayLineTest extends TestCase {

    public void testIsSeparator()
    {
        Assert.assertTrue(new ArrayLine("+---+").isSeparator());
        Assert.assertTrue(new ArrayLine("+--+-+").isSeparator());

        Assert.assertFalse(new ArrayLine("+++").isSeparator());
        Assert.assertFalse(new ArrayLine("| a | b | c |").isSeparator());
    }
}
