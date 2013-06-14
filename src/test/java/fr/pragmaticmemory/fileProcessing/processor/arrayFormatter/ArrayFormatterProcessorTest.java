package fr.pragmaticmemory.fileProcessing.processor.arrayFormatter;
import fr.pragmaticmemory.TestUtils;
import fr.pragmaticmemory.fileProcessing.processor.arrayFormatter.ArrayFormatterProcessor;
import java.util.List;
import junit.framework.Assert;
import junit.framework.TestCase;

public class ArrayFormatterProcessorTest extends TestCase {

    public void testFormatArray() throws Exception {
        List<String> unformattedArrayLines = TestUtils.getTestFileLines(getClass(), "unformattedArray.txt");
        List<String> formattedArrayLines = TestUtils.getTestFileLines(getClass(), "formattedArray.txt");
        ArrayFormatterProcessor processor = new ArrayFormatterProcessor();
        Assert.assertEquals(formattedArrayLines, processor.processFileContent(unformattedArrayLines));
    }
}
