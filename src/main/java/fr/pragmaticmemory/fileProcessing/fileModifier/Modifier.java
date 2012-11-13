package fr.pragmaticmemory.fileProcessing.fileModifier;
import fr.pragmaticmemory.fileProcessing.fileProvider.RouteProvider;

public abstract class Modifier {

    protected RouteProvider routeProvider;


    protected Modifier(RouteProvider routeProvider) {
        this.routeProvider = routeProvider;
    }


    public void process() throws Exception {
        for (int i = 0, size = routeProvider.getRouteNumber(); i < size; i++) {
            process(i);
        }
    }


    protected abstract void process(int routeIndex) throws Exception;
}
