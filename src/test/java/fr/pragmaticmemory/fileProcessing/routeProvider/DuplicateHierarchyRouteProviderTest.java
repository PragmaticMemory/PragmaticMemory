package fr.pragmaticmemory.fileProcessing.routeProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.NoFileProvider;
import java.io.File;
import junit.framework.Assert;
import junit.framework.TestCase;

public class DuplicateHierarchyRouteProviderTest extends TestCase {

    public void testGetDestinationFile() throws Exception {
        DuplicateHierarchyRouteProvider routeProvider
              = new DuplicateHierarchyRouteProvider(new NoFileProvider(), new File("D:\\SaveDirectory"));
        File destinationFile = routeProvider.getDestinationFile(new File("E:\\Root\\Example.txt"));
        Assert.assertEquals("D:\\SaveDirectory\\E\\Root\\Example.txt", destinationFile.getAbsolutePath());
    }
}
