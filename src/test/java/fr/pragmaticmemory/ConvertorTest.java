package fr.pragmaticmemory;
import java.util.List;
import junit.framework.Assert;
import junit.framework.TestCase;

public class ConvertorTest extends TestCase {

    private final Convertor convertor = new Convertor(2);


    public void testConvertFromJavaToXml() throws Exception {
        final List<String> inputJavaLines = TestUtils.getTestFileLines(getClass(), "Java.txt");
        final List<String> expectedXmlLines = TestUtils.getTestFileLines(getClass(), "Xml.txt");

        final List<String> actualXmlLines = convertor.convert("code2xml", inputJavaLines);
        Assert.assertEquals(expectedXmlLines, actualXmlLines);
    }


    public void testConvertFromXmlToJava() throws Exception {
        final List<String> inputXmlLines = TestUtils.getTestFileLines(getClass(), "Xml.txt");
        final List<String> expectedJavaLines = TestUtils.getTestFileLines(getClass(), "Java.txt");

        final List<String> actualJavaLines = convertor.convert("xml2code", inputXmlLines);

        Assert.assertEquals(expectedJavaLines, actualJavaLines);
    }
}
