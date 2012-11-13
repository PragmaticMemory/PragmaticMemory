package fr.pragmaticmemory.fileProcessing.fileProvider;
import java.io.File;
import java.net.URL;
import java.util.List;
import junit.framework.TestCase;

public class IdentityDirectoryChildrenRouteProviderTest extends TestCase {

    public void testGetRecursiveDirectoryFiles() throws Exception {
        final URL url = getClass().getResource("Dir-1A");
        IdentityDirectoryChildrenRouteProvider provider = new IdentityDirectoryChildrenRouteProvider(new File(url.getPath()));
        final List<File> allFile = provider.getFileList();
        assertEquals(8, allFile.size());
        assertEquals("File-2A.txt", allFile.get(0).getName());
        assertEquals("File-2A-A.txt", allFile.get(1).getName());
        assertEquals("File-2A-B.txt", allFile.get(2).getName());
        assertEquals("File-2A-C.txt", allFile.get(3).getName());
        assertEquals("File-2B-A.txt", allFile.get(4).getName());
        assertEquals("File-2B-B.txt", allFile.get(5).getName());
        assertEquals("File-2B-C.txt", allFile.get(6).getName());
        assertEquals("File-2B-D.txt", allFile.get(7).getName());
    }


    public void testNotRecursiveDirectoryFiles() throws Exception {
        final URL url = getClass().getResource("Dir-1A");
        IdentityDirectoryChildrenRouteProvider provider = new IdentityDirectoryChildrenRouteProvider(new File(url.getPath()), false);
        final List<File> allFile = provider.getFileList();
        assertEquals(1, allFile.size());
        assertEquals("File-2A.txt", allFile.get(0).getName());
    }
}
