package fr.pragmaticmemory.fileProcessing.routeProvider;
import fr.pragmaticmemory.fileProcessing.routeProvider.StringListReader.ReaderData;
import java.io.BufferedReader;
import java.util.Arrays;
import junit.framework.Assert;
import junit.framework.TestCase;

public class StringListReaderTest extends TestCase {

    static final String LINE_1 = "Première ligne";  // 0  - 14 (\n)
    static final String LINE_2 = "Seconde ligne";   // 15 - 28 (\n)
    static final String LINE_3 = "Troisième ligne"; // 29 - 44 (\n)
    StringListReader stringListReader = new StringListReader(Arrays.<String>asList(LINE_1, LINE_2, LINE_3));


    @Override
    public void setUp() throws Exception {

    }


    public void testReader() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(stringListReader);
        String s1 = bufferedReader.readLine();
        String s2 = bufferedReader.readLine();
        System.out.println();
    }


    public void testGetReaderData() throws Exception {
        Assert.assertEquals(new ReaderData(0, 0), stringListReader.getReaderData(0));
        Assert.assertEquals(new ReaderData(0, 5), stringListReader.getReaderData(5));
        Assert.assertEquals(new ReaderData(0, 14), stringListReader.getReaderData(14));
        Assert.assertEquals(new ReaderData(1, 0), stringListReader.getReaderData(15));
        Assert.assertEquals(new ReaderData(1, 1), stringListReader.getReaderData(16));
        Assert.assertEquals(new ReaderData(1, 13), stringListReader.getReaderData(28));
        Assert.assertEquals(new ReaderData(2, 0), stringListReader.getReaderData(29));
        Assert.assertEquals(new ReaderData(2, 15), stringListReader.getReaderData(44));
        Assert.assertEquals(new ReaderData(2, 16), stringListReader.getReaderData(45));
    }
}
