package fr.pragmaticmemory.fileProcessing.core;

public abstract class Processor {

    protected final RouteProvider routeProvider;


    protected Processor(RouteProvider routeProvider) {
        this.routeProvider = routeProvider;
    }


    public void process() throws Exception {
        for (int i = 0, size = routeProvider.getRouteNumber(); i < size; i++) {
            process(i);
        }
    }


    protected abstract void process(int routeIndex) throws Exception;
}
