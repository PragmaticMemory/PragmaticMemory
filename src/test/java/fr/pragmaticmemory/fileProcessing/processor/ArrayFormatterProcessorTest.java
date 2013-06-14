package fr.pragmaticmemory.fileProcessing.processor;
import fr.pragmaticmemory.TestUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import junit.framework.Assert;
import junit.framework.TestCase;

public class ArrayFormatterProcessorTest extends TestCase {

    public void testFill()
    {
        Set<Integer> separatorIndexSet = new TreeSet<Integer>();
        Set<Integer> dataIndexSet = new TreeSet<Integer>();
        List<String> inputLines = new ArrayList<String>();
//        inputLines.add();

    }



    public void testFormatArray() throws Exception {
        List<String> unformattedArrayLines = TestUtils.getTestFileLines(getClass(), "unformattedArray.txt");
        List<String> formattedArrayLines = TestUtils.getTestFileLines(getClass(), "formattedArray.txt");
        ArrayFormatterProcessor processor = new ArrayFormatterProcessor();
        Assert.assertEquals(formattedArrayLines, processor.processFileContent(unformattedArrayLines));
    }
}
