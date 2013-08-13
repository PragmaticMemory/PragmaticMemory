package fr.pragmaticmemory.fileProcessing.routeProvider;
import junit.framework.Assert;
import junit.framework.TestCase;

public class StringListWriterTest extends TestCase {

    static final char[] LINE = "Première ligne\nSeconde ligne\nTroisième ligne".toCharArray();
    //                          012345678901234 56789012345678 9012345678901234
    // 0  - 44 (14, 28, 44 -> \n)

//    public void testBufferedWriterLine() throws Exception {
//        StringListWriter stringListWriter = new StringListWriter();
//        BufferedWriter bufferedWriter = new BufferedWriter(stringListWriter);
//        bufferedWriter.write("Première ligne");
//        bufferedWriter.newLine();
//        bufferedWriter.write("Seconde ligne");
//        bufferedWriter.newLine();
//        bufferedWriter.write("Troisième ligne");
//        bufferedWriter.flush();
//
//        Assert.assertEquals(3, stringListWriter.getLineNumber());
//        Assert.assertEquals("Première ligne", stringListWriter.getLine(0));
//        Assert.assertEquals("Seconde ligne", stringListWriter.getLine(1));
//        Assert.assertEquals("Troisième ligne", stringListWriter.getLine(2));
//    }


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

//        writer.write(LINE, 33, 100);
        writer.write(LINE, 33, 11);
        Assert.assertEquals(3, writer.getLineNumber());
        Assert.assertEquals("remière ligne", writer.getLine(0));
        Assert.assertEquals("Seconde ligne", writer.getLine(1));
        Assert.assertEquals("Troisième ligne", writer.getLine(2));
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
