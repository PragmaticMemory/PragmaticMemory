package fr.pragmaticmemory;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import junit.framework.Assert;
import junit.framework.TestCase;

public class ConvertorTest extends TestCase {

    private final Convertor convertor = new Convertor(2);


    public void testConvertFromJavaToXml() throws Exception {
        final List<String> inputJavaLines = getTestFileLines("Java.txt");
        final List<String> expectedXmlLines = getTestFileLines("Xml.txt");

        final List<String> actualXmlLines = convertor.convert("code2xml", inputJavaLines);

        Assert.assertEquals(expectedXmlLines, actualXmlLines);
    }

    public void testConvertFromXmlToJava() throws Exception {
        final List<String> inputXmlLines = getTestFileLines("Xml.txt");
        final List<String> expectedJavaLines = getTestFileLines("Java.txt");

        final List<String> actualJavaLines = convertor.convert("xml2code", inputXmlLines);

        Assert.assertEquals(expectedJavaLines, actualJavaLines);
    }


    private List<String> getTestFileLines(String name) throws IOException {
        final InputStream javaStream = getClass().getResourceAsStream(name);
        Reader javaFileReader = new InputStreamReader(javaStream);
        return FileUtils.readLines(javaFileReader);
    }
}
