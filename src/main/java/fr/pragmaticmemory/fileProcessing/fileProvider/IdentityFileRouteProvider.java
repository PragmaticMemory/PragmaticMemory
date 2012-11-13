package fr.pragmaticmemory.fileProcessing.fileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
public class IdentityFileRouteProvider implements RouteProvider {
    private File file;


    public IdentityFileRouteProvider(File file) throws IOException {
        this.file = file;
    }


    public IdentityFileRouteProvider(String filePath) throws IOException {
        this(new File(filePath));
    }


    public int getRouteNumber() {
        return 1;
    }


    public Route getRoute(int routeIndex) throws FileNotFoundException {
        return new IdentityFileRoute(file);
    }
}
