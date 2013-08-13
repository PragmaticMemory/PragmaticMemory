package fr.pragmaticmemory.fileProcessing.routeProvider;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import junit.framework.Assert;
import junit.framework.TestCase;

public class StringListReaderTest extends TestCase {

    static final String LINE_1 = "Première ligne";  // 0  - 14 (\n)
    static final String LINE_2 = "Seconde ligne";   // 15 - 28 (\n)
    static final String LINE_3 = "Troisième ligne"; // 29 - 44 (\n)
    static final StringListReader STRING_LIST_READER = new StringListReader(Arrays.<String>asList(LINE_1,
                                                                                                  LINE_2,
                                                                                                  LINE_3));

//    public static void main(String[] args) throws IOException {
//        char[] cbuffer = new char[100];
//        StringReader stringReader = new StringReader(LINE_1);
//        int read = stringReader.read(cbuffer, 3, 14);
//        int read2 = stringReader.read(cbuffer, 3, 1);
//        stringReader.reset();
//        int read3 = stringReader.read(cbuffer, 3, 14);
//        System.out.println(read);
//        System.out.println(read2);
//        System.out.println(read3);
//        System.out.println(cbuffer);
//    }


    @Override
    public void setUp() throws Exception {
        STRING_LIST_READER.reset();
    }


    public void testBufferedReaderReadLine() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(STRING_LIST_READER);
        Assert.assertEquals(LINE_1, bufferedReader.readLine());
        Assert.assertEquals(LINE_2, bufferedReader.readLine());
        Assert.assertEquals(LINE_3, bufferedReader.readLine());
    }


    public void testRead() throws Exception {
        assetRead(8, "Première", 1, 8);
        assetRead(7, " ligne\n", 0, 7);
        assetRead(13, "Seconde ligne", 0, 13);
        assetRead(1, "\n", 0, 1);
        assetRead(16, "Troisième ligne\n", 0, 16);
        STRING_LIST_READER.reset();
        assetRead(8, "Première", 1, 8);
        assetRead(14, " ligne\nSeconde", 1, 14);
        STRING_LIST_READER.reset();
        assetRead(45, "Première ligne\nSeconde ligne\nTroisième ligne\n", 0, 100);
    }


    private void assetRead(int expectedRead, String expectedString, int offset, int length) throws IOException {
        char[] cBuffer = new char[offset + length];
        int read = STRING_LIST_READER.read(cBuffer, offset, length);
        Assert.assertEquals(expectedString, new String(cBuffer).substring(offset, offset + read));
        Assert.assertEquals(expectedRead, read);
    }
}
