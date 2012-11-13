package fr.pragmaticmemory.fileProcessing.fileProvider;
import java.io.File;
import java.net.URL;
import java.util.List;
import junit.framework.TestCase;

public class IdentityFileRouteProviderTest extends TestCase {

    public void testGetSingleFile() throws Exception {
        final URL url = getClass().getResource("File-1A.txt");
        SingleFileProvider provider = new SingleFileProvider(new File(url.getPath()));
        final List<File> allFile = provider.getAllFile();
        assertEquals(1, allFile.size());
        assertEquals("File-1A.txt", allFile.get(0).getName());
    }
}
