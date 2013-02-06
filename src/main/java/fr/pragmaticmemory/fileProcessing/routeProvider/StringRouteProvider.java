package fr.pragmaticmemory.fileProcessing.routeProvider;

import fr.pragmaticmemory.fileProcessing.core.Route;
import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
public class StringRouteProvider implements RouteProvider {
    private final StringRoute stringRoute;


    public StringRouteProvider(String inputString) {
        stringRoute = new StringRoute(inputString);
    }


    public int getRouteNumber() {
        return 1;
    }


    public Route getRoute(int routeIndex) throws FileNotFoundException {
        return stringRoute;
    }


    public String getOutputString() {
        return stringRoute.getOutputString();
    }


    private class StringRoute implements Route {

        private final String inputString;
        private StringWriter stringWriter;


        private StringRoute(String inputString) {
            this.inputString = inputString;
        }


        public Reader getReader() {
            return new StringReader(inputString);
        }


        public Writer getWriter() {
            stringWriter = new StringWriter();
            return stringWriter;
        }


        public String getOutputString() {
            return stringWriter.toString();
        }
    }
}