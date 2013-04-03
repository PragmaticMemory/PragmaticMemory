package fr.pragmaticmemory.fileProcessing.core;

public abstract class Processor {

    public void process(RouteProvider routeProvider) throws Exception {
        for (int routeIndex = 0, size = routeProvider.getRouteNumber(); routeIndex < size; routeIndex++) {
            process(routeProvider.getRoute(routeIndex));
        }
    }


    protected abstract void process(Route route) throws Exception;
}
