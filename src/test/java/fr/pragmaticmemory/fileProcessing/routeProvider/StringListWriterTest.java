package fr.pragmaticmemory.fileProcessing.routeProvider;
import java.io.BufferedWriter;
import java.io.IOException;
import junit.framework.Assert;
import junit.framework.TestCase;

public class StringListWriterTest extends TestCase {

    static final char[] LINE = "Premi�re ligne\nSeconde ligne\nTroisi�me ligne".toCharArray();
    //                          012345678901234 56789012345678 9012345678901234
    // 0  - 44 (14, 28, 44 -> \n)


    public void testBufferedWriter() throws Exception {
        StringListWriter stringListWriter = new StringListWriter("\r\n");
        BufferedWriter bufferedWriter = new BufferedWriter(stringListWriter);
        bufferedWriter.write("Premi�re ligne");
        bufferedWriter.newLine();
        bufferedWriter.write("Seconde ligne");
        bufferedWriter.newLine();
        bufferedWriter.write("Troisi�me ligne");
        bufferedWriter.flush();

        Assert.assertEquals(3, stringListWriter.getLineNumber());
        Assert.assertEquals("Premi�re ligne", stringListWriter.getLine(0));
        Assert.assertEquals("Seconde ligne", stringListWriter.getLine(1));
        Assert.assertEquals("Troisi�me ligne", stringListWriter.getLine(2));
    }


    public void testWriteWithLongSeparator() throws IOException {
        StringListWriter writer = new StringListWriter("\r\n");
        writer.write("01\r\nab\r\ncde", 0, 7);
        writer.write("01\r\nab\r\ncde", 7, 4);
        writer.flush();

        Assert.assertEquals(3, writer.getLineNumber());
        Assert.assertEquals("01", writer.getLine(0));
        Assert.assertEquals("ab", writer.getLine(1));
        Assert.assertEquals("cde", writer.getLine(2));
    }


    public void testWriteWithShortSeparator() throws Exception {
        StringListWriter writer = new StringListWriter("\n");
        Assert.assertEquals(0, writer.getLineNumber());

        writer.write(LINE, 1, 7);
        writer.flush();
        Assert.assertEquals(1, writer.getLineNumber());
        Assert.assertEquals("remi�re", writer.getLine(0));

        writer.write(LINE, 8, 7);
        writer.flush();
        Assert.assertEquals(2, writer.getLineNumber());
        Assert.assertEquals("remi�re ligne", writer.getLine(0));
        Assert.assertEquals("", writer.getLine(1));

        writer.write(LINE, 15, 18);
        writer.flush();
        Assert.assertEquals(3, writer.getLineNumber());
        Assert.assertEquals("remi�re ligne", writer.getLine(0));
        Assert.assertEquals("Seconde ligne", writer.getLine(1));
        Assert.assertEquals("Troi", writer.getLine(2));

        writer.write(LINE, 33, 11);
        writer.flush();
        Assert.assertEquals(3, writer.getLineNumber());
        Assert.assertEquals("remi�re ligne", writer.getLine(0));
        Assert.assertEquals("Seconde ligne", writer.getLine(1));
        Assert.assertEquals("Troisi�me ligne", writer.getLine(2));
    }


    public static void main(String[] args) {
        String string = "a\n";
        String[] split = string.split("\n", -1);
        string = "a\nb";
        split = string.split("\\n", -1);
        string = "\n";
        split = string.split("\\n", -1);
        System.out.println();
    }
}
