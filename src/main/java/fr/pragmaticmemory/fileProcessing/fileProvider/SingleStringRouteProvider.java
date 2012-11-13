package fr.pragmaticmemory.fileProcessing.fileProvider;

import java.io.FileNotFoundException;
public class SingleStringRouteProvider implements RouteProvider {
    private StringRoute stringRoute;


    public SingleStringRouteProvider(String line) {
        stringRoute = new StringRoute(line);
    }


    public int getRouteNumber() {
        return 1;
    }


    public Route getRoute(int routeIndex) throws FileNotFoundException {
        return stringRoute;
    }


    public String getResultString() {
        return stringRoute.getResultString();
    }
}
