package fr.pragmaticmemory.fileProcessing.core;

import java.io.FileNotFoundException;
public interface RouteProvider {

    abstract public int getRouteNumber();


    abstract public Route getRoute(int routeIndex) throws FileNotFoundException;
}
