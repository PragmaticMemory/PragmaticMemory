package fr.pragmaticmemory.fileProcessing.routeProvider;
import junit.framework.Assert;
import junit.framework.TestCase;

public class StringListWriterTest extends TestCase {

    static final char[] LINE = "Première ligne\nSeconde ligne\nTroisième ligne".toCharArray();
    //                          012345678901234 56789012345678 9012345678901234
    // 0  - 44 (14, 28, 44 -> \n)


    public void testWrite() throws Exception {
        StringListWriter writer = new StringListWriter();
        Assert.assertEquals(0, writer.getLineNumber());

        writer.write(LINE, 1, 7);
        Assert.assertEquals(1, writer.getLineNumber());
        Assert.assertEquals("remière", writer.getLine(0));

        writer.write(LINE, 8, 7);
        Assert.assertEquals(2, writer.getLineNumber());
        Assert.assertEquals("remière ligne", writer.getLine(0));
        Assert.assertEquals("", writer.getLine(1));

        writer.write(LINE, 15, 18);
        Assert.assertEquals(3, writer.getLineNumber());
        Assert.assertEquals("remière ligne", writer.getLine(0));
        Assert.assertEquals("Seconde ligne", writer.getLine(1));
        Assert.assertEquals("Troi", writer.getLine(2));

        writer.write(LINE, 33, 100);
        Assert.assertEquals("remière ligne", writer.getLine(0));
        Assert.assertEquals("Seconde ligne", writer.getLine(1));
        Assert.assertEquals("Troisième ligne", writer.getLine(2));
    }
}
