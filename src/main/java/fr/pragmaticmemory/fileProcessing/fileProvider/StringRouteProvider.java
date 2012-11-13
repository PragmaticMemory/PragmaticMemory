package fr.pragmaticmemory.fileProcessing.fileProvider;

import java.io.FileNotFoundException;
public class StringRouteProvider implements RouteProvider {
    private StringRoute stringRoute;


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
}
