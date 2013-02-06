package fr.pragmaticmemory.fileProcessing.processor;
import fr.pragmaticmemory.TestUtils;
import java.util.List;
import junit.framework.Assert;
import junit.framework.TestCase;

public class ArrayFormatterProcessorTest extends TestCase {

    public void testFormatArray() throws Exception {
        List<String> unformattedArrayLines = TestUtils.getTestFileLines(getClass(), "unformattedArray.txt");
        List<String> formattedArrayLines = TestUtils.getTestFileLines(getClass(), "formattedArray.txt");
        ArrayFormatterProcessor processor = new ArrayFormatterProcessor(null);
        Assert.assertEquals(formattedArrayLines, processor.processFileContent(unformattedArrayLines));
    }
}
