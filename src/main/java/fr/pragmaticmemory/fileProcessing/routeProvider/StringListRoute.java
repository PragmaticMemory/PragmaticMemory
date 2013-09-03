package fr.pragmaticmemory.fileProcessing.routeProvider;
import fr.pragmaticmemory.fileProcessing.core.Route;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

public class StringListRoute implements Route {

    private List<String> stringList;
    private StringListWriter stringWriter;


    public StringListRoute(List<String> stringList) {
        this.stringList = stringList;
    }


    public Reader getReader() throws Exception {
        return new StringListReader(stringList);
    }


    public Writer getWriter() throws Exception {
        stringWriter = new StringListWriter("\n");
        return stringWriter;
    }


    public List<String> getResultList() {
        return stringWriter.getStringList();
    }
}
