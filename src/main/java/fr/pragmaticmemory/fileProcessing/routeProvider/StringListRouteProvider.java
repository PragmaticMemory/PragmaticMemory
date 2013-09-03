package fr.pragmaticmemory.fileProcessing.routeProvider;

import fr.pragmaticmemory.fileProcessing.core.Route;
import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
import java.io.FileNotFoundException;
import java.util.List;
public class StringListRouteProvider implements RouteProvider {

    StringListRoute stringListRoute;


    public StringListRouteProvider(List<String> stringList) {
        stringListRoute = new StringListRoute(stringList);
    }


    public int getRouteNumber() {
        return 1;
    }


    public Route getRoute(int routeIndex) throws FileNotFoundException {
        return stringListRoute;
    }


    public List<String> getResultString() {
        return stringListRoute.getResultList();
    }
}
