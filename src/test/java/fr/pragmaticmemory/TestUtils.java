package fr.pragmaticmemory;

import fr.pragmaticmemory.fileProcessing.utils.FileUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
public class TestUtils {

    public static List<String> getTestFileLines(Class<?> aClass, String fileName) throws IOException {
        final InputStream javaStream = aClass.getResourceAsStream(fileName);
        Reader javaFileReader = new InputStreamReader(javaStream);
        return FileUtils.readLines(javaFileReader);
    }
}


